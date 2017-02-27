package info.solidsoft.java8;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implement simple utility methods for running blocks of code in multiple threads
 */

public class J05_MultiRunnerTest {

	@Test
	public void shouldExecuteTasksInMultipleThreads() throws Exception {
		//given
		final Map<Long, String> threads = new ConcurrentHashMap<>();

		//when
		IntStream.range(0, 100).forEach(i ->
						MultiRunner.runMultiThreaded(new Runnable() {
                            @Override
                            public void run() {
                            	final Thread thread = Thread.currentThread();
                            	threads.put(thread.getId(), thread.getName());
                            }
                        })
		);

		//then
		await().until(() -> assertThat(threads.size()).isGreaterThan(1));
		assertThat(threads).doesNotContainKey(Thread.currentThread().getId());
	}

	@Test
	public void shouldRunMultipleBlocks() throws Exception {
		//given
		final LongAdder counter = new LongAdder();

		//when
		MultiRunner.runMultiThreaded(Arrays.asList(
				() -> counter.add(1),
				() -> counter.add(2),
				() -> counter.add(3)
		));

		//then
		await().until(() -> assertThat(counter.sum()).isEqualTo(1L + 2L + 3L));
	}

	@Test
	public void shouldRunTheSameTaskMultipleTimes() throws Exception {
		//given
		final LongAdder counter = new LongAdder();

		//when
		MultiRunner.runMultiThreaded(3, () -> counter.add(7));

		//then
		await().until(() -> assertThat(counter.sum()).isEqualTo(7L + 7L + 7L));
	}

}