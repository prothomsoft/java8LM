package info.solidsoft.java8.stackoverflow;

import com.google.common.base.Throwables;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//Duplication with FallbackStubClient
public class DeterministicStubbedClient implements StackOverflowClient {

	private static final Logger log = LoggerFactory.getLogger(DeterministicStubbedClient.class);

	public static final String STUBBED_SO_JAVA_QUESTION = "How to generate xml report with maven depencency?";
	public static final String STUBBED_SO_SCALA_QUESTION = "Update a timestamp SettingKey in an sbt 0.12 task";
	public static final String STUBBED_SO_GROOVY_QUESTION = "Reusing Grails variables inside Config.groovy";
	public static final String STUBBED_SO_CLOJURE_QUESTION = "Merge two comma delimited strings in Clojure";

	@Override
	public String mostRecentQuestionAbout(String tag) {
		switch(tag) {
			case "java":
				return STUBBED_SO_JAVA_QUESTION;
			case "scala":
				return STUBBED_SO_SCALA_QUESTION;
			case "groovy":
				return STUBBED_SO_GROOVY_QUESTION;
			case "clojure":
				return STUBBED_SO_CLOJURE_QUESTION;
			default:
				throw new UnsupportedOperationException("Unsupported tag: " + tag);
		}
	}

	@Override
	public Document mostRecentQuestionsAbout(String tag) {
		return loadStubHtmlFromDisk(tag);
	}

	private Document loadStubHtmlFromDisk(String tag) {
		try {
			final String html = IOUtils.toString(getClass().getResource("/" + tag + "-questions.html"));
			return Jsoup.parse(html);
		} catch (IOException e1) {
			throw Throwables.propagate(e1);
		}
	}
}
