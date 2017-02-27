package info.solidsoft.java8;

import info.solidsoft.java8.util.AbstractStubbedFuturesTest;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class J26b_ErrorHandlingTest extends AbstractStubbedFuturesTest {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Test
	public void shouldFailOnGetWhenNoExplicitErrorHandling() throws Exception {
		//given
		AtomicBoolean receivedQuestion = new AtomicBoolean(false);
		CompletableFuture<String> failingQuestion = questions("php");

		//when
		failingQuestion.thenAccept(q -> {
			log.debug("Newest question: {}", q);
			receivedQuestion.set(true);
		});

		//then
		await().until(() -> assertThat(failingQuestion).hasFailedWithThrowableThat()
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Unsupported php"));
		assertThat(receivedQuestion.get()).isFalse();

//		failingQuestion.get();  //Would fail on get() or join()
	}

	/**
	 * Hint: CompletableFuture.handle()
	 */
	@Test
	public void shouldHandleException() throws Exception {
		//given
		CompletableFuture<String> failingQuestion = questions("php");

		//when
		CompletableFuture<String> handled = null;   //failingQuestion.
		//then
		await().until(() -> assertThat(handled).isCompleted());
		assertThat(handled.get()).contains("No PHP question");
		//and
		log.debug("Handled: {}", handled.get());
	}

	/**
	 * Hint: CompletableFuture.exceptionally()
	 */
	@Test
	public void shouldHandleExceptionally() throws Exception {
		//given
		CompletableFuture<String> failingQuestion = questions("php");

		//when
		CompletableFuture<String> handled = null;   //failingQuestion.

		//then
		await().until(() -> assertThat(handled).isCompleted());
		assertThat(handled.get()).isEqualTo("Sorry, try again later");
		//and
		log.debug("Handled exceptionally: {}", handled.get());
	}
}
