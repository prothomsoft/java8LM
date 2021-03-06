package info.solidsoft.java8;

import com.google.common.base.Throwables;
import info.solidsoft.java8.agent.Agent;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class J29_AsyncAgentFuturesTest {

	private ExecutorService pool = Executors.newFixedThreadPool(100);

	@After
	public void stopPool() {
		pool.shutdown();
	}

	@Test
	public void shouldCompleteFutureWhenSendDone() throws ExecutionException, InterruptedException {
		//given
		final Agent<Integer> agent = Agent.create(1, pool);

		//when
		final CompletableFuture<Integer> future = agent.sendAndGet(x -> x + 2);

		//then
		assertThat(future.get()).isEqualTo(1 + 2);
	}

	@Test
	public void shouldCompleteWhenAllPendingFuturesAreDone() throws ExecutionException, InterruptedException {
		//given
		final Agent<String> agent = Agent.create("", pool);

		//when
		agent.send(s -> s + "1");
		agent.send(s -> s + "2");
		final CompletableFuture<String> future = agent.sendAndGet(s -> s + "3");

		//then
		assertThat(future.get()).isEqualTo("123");
	}

	@Test
	public void shouldWaitUntilConditionIsMetOnTwoAgents() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<String> agentOne = Agent.create("Abc", pool);
		final Agent<String> agentTwo = Agent.create("Def", pool);

		//when
		final CompletableFuture<String> futureOne = agentOne.completeIf(String::isEmpty);
		final CompletableFuture<String> futureTwo = agentTwo.completeIf(String::isEmpty);
		agentOne.send(s -> "");
		agentTwo.send(s -> "");

		//then
		futureOne.get(1, TimeUnit.SECONDS);
		futureTwo.get(1, TimeUnit.SECONDS);
		assertThat(agentOne.get()).isEmpty();
		assertThat(agentTwo.get()).isEmpty();
	}

	@Test
	public void shouldWaitForTwoAgents() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<String> agentOne = Agent.create("", pool);
		final Agent<String> agentTwo = Agent.create("", pool);

		//when
		final CompletableFuture<String> futureOne = agentOne.sendAndGet(s -> s + "One");
		final CompletableFuture<String> futureTwo = agentTwo.sendAndGet(s -> s + "Two");

		//then
		CompletableFuture<String> both = null;  //?
		assertThat(both.get(1, TimeUnit.SECONDS)).isEqualTo("OneTwo");
	}

	@Test
	public void shouldReturnCurrentValue() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<Integer> agent = Agent.create(1, pool);

		//when
		agent.send(x -> x + 2);
		final CompletableFuture<Integer> future = agent.getAsync();

		//then
		assertThat(future.get(1, TimeUnit.SECONDS)).isEqualTo(1 + 2);
	}

	@Test
	public void shouldReflectAllPriorChangesWhenAsyncGet() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<Integer> agent = Agent.create(1, pool);
		//and
		final CountDownLatch latch = new CountDownLatch(1);
		int latchAwaitInMillis = 200;
		int isCompletedDurationInMillis = latchAwaitInMillis * 2;
		//and
		agent.send(x -> {
			awaitOnLatchInMillis(latch, latchAwaitInMillis);
			return x + 2;
		});

		//when
		final CompletableFuture<Integer> future = agent.getAsync();

		//then
		assertThat(latch.getCount()).isEqualTo(1);
		//and
		await().atMost(isCompletedDurationInMillis, TimeUnit.MILLISECONDS).until(() -> assertThat(future).isCompleted());
		assertThat(future.get()).isEqualTo(1 + 2);
	}

	private void awaitOnLatchInMillis(CountDownLatch latch, int latchAwaitInMillis) {
		try {
			latch.await(latchAwaitInMillis, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Throwables.propagate(e);
		}
	}

	@Test
	public void shouldNotSeeChangesMadeAfterAsyncGet() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<Integer> agent = Agent.create(1, pool);

		//when
		agent.send(x -> x + 2);
		final CompletableFuture<Integer> future = agent.getAsync();
		agent.send(x -> x + 3);

		//then
		assertThat(future.get(1, TimeUnit.SECONDS)).isEqualTo(1 + 2);
	}

	@Test
	public void shouldCompleteWhenConditionIsMet() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<String> agent = Agent.create("", pool);

		//when
		final CompletableFuture<String> future = agent.completeIf(s -> s.length() >= 2);
		agent.send(s -> s + "1");
		agent.send(s -> s + "2");
		agent.send(s -> s + "3");

		//then
		assertThat(future.get(1, TimeUnit.SECONDS)).isEqualTo("12");
	}

	@Test
	public void shouldCompleteImmediatelyIfConditionAlreadyMet() throws ExecutionException, InterruptedException, TimeoutException {
		//given
		final Agent<String> agent = Agent.create("", pool);

		//when
		final CompletableFuture<String> future = agent.completeIf(String::isEmpty);
		agent.send(s -> s + "1");

		//then
		assertThat(future.get(1, TimeUnit.SECONDS)).isEmpty();
	}

}