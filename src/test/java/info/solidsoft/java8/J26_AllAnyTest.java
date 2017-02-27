package info.solidsoft.java8;

import info.solidsoft.java8.util.AbstractStubbedFuturesTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static com.jayway.awaitility.Awaitility.await;
import static info.solidsoft.java8.stackoverflow.DeterministicStubbedClient.STUBBED_SO_CLOJURE_QUESTION;
import static info.solidsoft.java8.stackoverflow.DeterministicStubbedClient.STUBBED_SO_GROOVY_QUESTION;
import static info.solidsoft.java8.stackoverflow.DeterministicStubbedClient.STUBBED_SO_JAVA_QUESTION;
import static info.solidsoft.java8.stackoverflow.DeterministicStubbedClient.STUBBED_SO_SCALA_QUESTION;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class J26_AllAnyTest extends AbstractStubbedFuturesTest {

	@Test
	public void shouldWaitForAllOfQuestions() {
		final CompletableFuture<String> clojure = questions("clojure");
		final CompletableFuture<String> java = questions("java");
		final CompletableFuture<String> scala = questions("scala");
		final CompletableFuture<String> groovy = questions("groovy");

		final CompletableFuture<Void> allCompleted = null;  //CompletableFuture.

		await().until(() -> assertThat(allCompleted).isCompleted());
		assertThat(java).isCompletedWithValue(STUBBED_SO_JAVA_QUESTION);
		assertThat(scala).isCompletedWithValue(STUBBED_SO_SCALA_QUESTION);
		assertThat(clojure).isCompletedWithValue(STUBBED_SO_CLOJURE_QUESTION);
		assertThat(groovy).isCompletedWithValue(STUBBED_SO_GROOVY_QUESTION);
	}

	@Test
	public void shouldWaitJustForAnyOfQuestion() {
		final CompletableFuture<String> java = questions("java");
		final CompletableFuture<String> scala = questions("scala");
		final CompletableFuture<String> clojure = questions("clojure");
		final CompletableFuture<String> groovy = questions("groovy");

		final CompletableFuture<Object> firstCompleted = null;  //CompletableFuture.

		await().until(() -> assertThat(firstCompleted).isCompleted());
		assertThat(firstCompleted.join()).isIn(STUBBED_SO_JAVA_QUESTION, STUBBED_SO_SCALA_QUESTION, STUBBED_SO_CLOJURE_QUESTION,
				STUBBED_SO_GROOVY_QUESTION);
	}
}
