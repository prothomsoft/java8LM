package info.solidsoft.java8;

import info.solidsoft.java8.agent.Agent;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@Ignore
public class J13_AsyncAgentTest {

	private ExecutorService pool = Executors.newFixedThreadPool(100);

	@After
	public void stopPool() {
		pool.shutdown();
	}

	@Test
	public void newAgentShouldHaveInitialValue() {
		//given
		final Agent<Integer> agent = Agent.create(42, pool);

		//when
		final int actual = agent.get();

		//then
		assertThat(actual).isEqualTo(42);
	}

	@Test
	public void shouldApplyTwoChanges() {
		//given
		final Agent<BigInteger> agent = Agent.create(BigInteger.ONE, pool);

		//when
		agent.send(x -> x.add(BigInteger.ONE));
		agent.send(x -> x.add(BigInteger.TEN));

		//then
		await().until(() -> assertThat(agent.get()).isEqualTo(BigInteger.valueOf(1 + 1 + 10)));
	}

	@Test
	public void shouldApplyChangesFromOneThreadInOrder() {
		//given
		final Agent<String> agent = Agent.create("", pool);

		//when
		agent.send(s -> s + "A");
		agent.send(s -> s + "B");
		agent.send(s -> s + "C");
		agent.send(String::toLowerCase);
		agent.send(s -> "D" + s);

		//then
		await().until(() -> assertThat(agent.get()).isEqualTo("Dabc"));
	}

	@Test
	public void shouldRunInDifferentThread() {
		//given
		final long mainThreadId = Thread.currentThread().getId();
		final Agent<Long> agent = Agent.create(mainThreadId, pool);

		//when
		agent.send(x -> Thread.currentThread().getId());

		//then
		await().until(() -> assertThat(agent.get()).isNotEqualTo(mainThreadId));
	}

	@Test
	public void aLotOfAgents() throws InterruptedException {
		//given
		final int totalAgents = 20_000;
		final List<Agent<String>> agents = IntStream
				.range(0, totalAgents)
				.boxed()
				.map(i -> Agent.create("", pool))
				.collect(toList());

		//when
		agents.forEach(a -> {
			a.send(s -> s + "a");
			a.send(s -> s + "b");
			a.send(s -> s + "c");
			a.send(s -> s + "d");
			a.send(s -> s + "e");
			a.send(s -> s + "f");
		});

		//then
		agents.forEach(this::waitForCorrectResult);
	}

	private void waitForCorrectResult(Agent<String> a) {
		await()
				.pollInterval(1, TimeUnit.MILLISECONDS)
				.pollDelay(1, TimeUnit.NANOSECONDS)
				.until(a::get, is("abcdef"));
	}

	@Test
	public void shouldMutateObjectInsideAgent() throws Exception {
		//given
		final Agent<Set<Integer>> agent = Agent.create(new HashSet<>(), pool);
		final int total = 10_000;

		//when
		IntStream
				.range(0, total)
				.forEach(i ->
						agent.send(set -> {
							set.add(i);
							return set;
						}));

		//then
		await().until(() -> assertThat(agent.get().size()).isEqualTo(total));
		//Question: Why assertThat(agent.get()).hasSize(total)) can be unsafe here?
	}

}