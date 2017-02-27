package info.solidsoft.java8;

import info.solidsoft.java8.util.AbstractStubbedFuturesTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

public class J26c_AsyncCallbackTest extends AbstractStubbedFuturesTest {

	private static final Logger log = LoggerFactory.getLogger(J26c_AsyncCallbackTest.class);

	private final ExecutorService poolAlpha = Executors.newFixedThreadPool(10, threadFactory("Alpha"));
	private final ExecutorService poolBeta = Executors.newFixedThreadPool(10, threadFactory("Beta"));
	private final ExecutorService poolGamma = Executors.newFixedThreadPool(10, threadFactory("Gamma"));

	@Test
	public void whichThreadInvokesCallbacks() throws Exception {
		final CompletableFuture<String> java = CompletableFuture
				.supplyAsync(() -> client.mostRecentQuestionAbout("java"), poolAlpha);
		final CompletableFuture<String> scala = CompletableFuture
				.supplyAsync(() -> client.mostRecentQuestionAbout("scala"), poolBeta);

		final CompletableFuture<String> first = java
				.applyToEither(scala, question -> {
					log.debug("First: {}", question);
					return question.toUpperCase();
				});

		first.thenAccept(q -> log.info("Sync: {}", q));
		first.thenAcceptAsync(q -> log.info("Async: {}", q));
		first.thenAcceptAsync(q -> log.info("Async (pool): {}", q), poolGamma);

		await().until(() -> assertThat(first).isCompleted());
	}

}
