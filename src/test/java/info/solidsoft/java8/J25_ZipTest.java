package info.solidsoft.java8;

import info.solidsoft.java8.util.AbstractStubbedFuturesTest;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class J25_ZipTest extends AbstractStubbedFuturesTest {

	private static final Logger log = LoggerFactory.getLogger(J25_ZipTest.class);

	/**
	 * Hint: CompletableFuture.thenCombine()
	 */
	@Test
	public void shouldCombineLengthOfBothQuestions() {
		final CompletableFuture<String> java = questions("java");
		final CompletableFuture<String> scala = questions("scala");

		final CompletableFuture<Integer> combineLengthOfBothQuestions = null;   //java.

		await().until(() -> assertThat(combineLengthOfBothQuestions).isCompleted());
		assertThat(combineLengthOfBothQuestions.join()).isEqualTo(98);
		log.debug("Total length: {}", combineLengthOfBothQuestions.join());
	}

	/**
	 * Hint: CompletableFuture.acceptEither()
	 */
	@Test
	public void shouldExecuteGetFirstWordFromOneOfTwoQuestion() {
		//given
		final CompletableFuture<String> java = questions("java");
		final CompletableFuture<String> scala = questions("scala");
		//and
		AtomicReference<Optional<String>> maybeFirstWordFromQuestion = new AtomicReference<>(Optional.empty());

		//when
		CompletableFuture<Void> oneWordExtracted = null;    //java.

		//then
		await().until(() -> assertThat(oneWordExtracted).isCompleted());
		//and
		String firstWordFromQuestion = maybeFirstWordFromQuestion.get().get();  //Value cannot be taken from CompletableFuture<Void>
		assertThat(firstWordFromQuestion).isIn("How", "Update");
		log.debug("First read word: {}", firstWordFromQuestion);
	}

	/**
	 * Hint: CompletableFuture.applyToEither()
	 */
	@Test
	public void shouldTransformOneOfTwoQuestionsToGetFirstWord() {
		//given
		final CompletableFuture<String> java = questions("java");
		final CompletableFuture<String> scala = questions("scala");

		//when
		final CompletableFuture<String> firstWordOfOneQuestion = null;  //java.

		//then
		await().until(() -> assertThat(firstWordOfOneQuestion).isCompleted());
		assertThat(firstWordOfOneQuestion.join()).isIn("How", "Update");
		log.debug("First read word: {}", firstWordOfOneQuestion.join());
	}

	private String getFirstWordFromQuestion(String question) {
		return question.split(" ")[0];
	}
}

