package info.solidsoft.java8.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import info.solidsoft.java8.stackoverflow.ArtificialSleepWrapper;
import info.solidsoft.java8.stackoverflow.FallbackStubClient;
import info.solidsoft.java8.stackoverflow.HttpStackOverflowClient;
import info.solidsoft.java8.stackoverflow.InjectErrorsWrapper;
import info.solidsoft.java8.stackoverflow.LoggingWrapper;
import info.solidsoft.java8.stackoverflow.StackOverflowClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class AbstractFuturesTest {

	private static final Logger log = LoggerFactory.getLogger(AbstractFuturesTest.class);

	protected final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory());

	@Rule
	public TestName testName = new TestName();

	private ThreadFactory threadFactory() {
		return threadFactory("Custom-pool");
	}

	protected ThreadFactory threadFactory(String nameFormat) {
		return new ThreadFactoryBuilder().setNameFormat(nameFormat + "-%d").build();
	}

	protected final StackOverflowClient client =
			new FallbackStubClient(
					new InjectErrorsWrapper(
							new LoggingWrapper(
									new ArtificialSleepWrapper(
											getRealClient()
									)
							), "php"
					)
			);

	@Before
	public void logTestStart() {
		log.debug("Starting: {}", testName.getMethodName());
	}

	@After
	public void stopPool() throws InterruptedException {
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
	}

	protected CompletableFuture<String> questions(String tag) {
		return CompletableFuture.supplyAsync(() ->
						client.mostRecentQuestionAbout(tag),
				executorService
		);
	}

	protected StackOverflowClient getRealClient() {
		return new HttpStackOverflowClient();
	}
}
