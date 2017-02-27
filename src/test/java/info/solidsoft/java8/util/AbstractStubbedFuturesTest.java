package info.solidsoft.java8.util;

import info.solidsoft.java8.stackoverflow.DeterministicStubbedClient;
import info.solidsoft.java8.stackoverflow.StackOverflowClient;

/**
 * Specialized abstract class providing deterministic stubbed questions from StackOverflow.
 * <p/>
 *
 * Use {@link AbstractFuturesTest} to get real questions from StackOverflow.
 */
public class AbstractStubbedFuturesTest extends AbstractFuturesTest {

	@Override
	protected StackOverflowClient getRealClient() {
		return new DeterministicStubbedClient();
	}
}
