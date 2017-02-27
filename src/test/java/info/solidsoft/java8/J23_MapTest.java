package info.solidsoft.java8;

import info.solidsoft.java8.util.AbstractStubbedFuturesTest;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class J23_MapTest extends AbstractStubbedFuturesTest {

	private static final Logger log = LoggerFactory.getLogger(J23_MapTest.class);

	@Test
	public void oldSchool() throws Exception {
		final CompletableFuture<Document> java = CompletableFuture.supplyAsync(() -> client.mostRecentQuestionsAbout("java"),
				executorService);

		final Document document = java.get();       //blocks
		final Element element = document.select("a.question-hyperlink").get(0);
		final String title = element.text();
		final int length = title.length();

		assertThat(length).isEqualTo(88);
		log.debug("Length: {}", length);
	}

	@Test
	public void callbacks() {
		//given
		final CompletableFuture<Document> java = CompletableFuture.supplyAsync(() -> client.mostRecentQuestionsAbout("java"),
				executorService);

		//problematic composition - CompletableFuture<Void> is returned - just callbacks
		java.thenAccept(document -> log.debug("Downloaded page with title: {}", document.title()));
	}

	/**
	 * Hint CompletableFuture.thenApply()
	 */
	@Test
	public void shouldComposeOperation() throws Exception {
		final CompletableFuture<Document> java = CompletableFuture.supplyAsync(() ->
				client.mostRecentQuestionsAbout("java"),
				executorService);

		final CompletableFuture<Element> titleElement = null;   //java.
		final CompletableFuture<String> titleText = null;       //titleElement.
		final CompletableFuture<Integer> length = null;         //titleText.

		await().until(() -> assertThat(length).isCompleted());
		assertThat(length.get()).isEqualTo(88);
		log.debug("Length: {}", length.get());
	}

	@Test
	public void shouldComposeOperationInChainedForm() throws Exception {
		final CompletableFuture<Document> java = CompletableFuture.supplyAsync(() ->
				client.mostRecentQuestionsAbout("java"),
				executorService);

		final CompletableFuture<Integer> length = null;     //java.

		await().until(() -> assertThat(length).isCompleted());
		assertThat(length.get()).isEqualTo(88);
		log.debug("Length: {}", length.get());
	}

}
