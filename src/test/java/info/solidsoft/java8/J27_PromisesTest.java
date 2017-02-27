package info.solidsoft.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;

public class J27_PromisesTest {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private FakeJmsMessageListener messageListener = new FakeJmsMessageListener();

	//Not a test in fact - just to grasp the idea
	public void promises() {
		final CompletableFuture<String> future = newPromiseWaitingForJmsMessageWithGivenId(77);

		log.debug("Message has been received: {}", future.join());   //Would be - there is no JMS queue connected
	}

	private CompletableFuture<String> newPromiseWaitingForJmsMessageWithGivenId(int messageId) {
		CompletableFuture<String> promise = new CompletableFuture<>();
		messageListener.registerPromiseForMessageWithId(messageId, promise);
		return promise;
	}

	private static class FakeJmsMessageListener {

		private long requestedMessageId;
		private CompletableFuture<String> promise;

		void registerPromiseForMessageWithId(int messageId, CompletableFuture<String> promise) {
			this.requestedMessageId = messageId;
			this.promise = promise;
		}

		//method called from another thread when JMS message arrived in specific queue
		void onMessage(int messageId, String messageBody) {
			if (messageId == requestedMessageId) {
				promise.complete(messageBody);
			}
		}
	}
}
