package info.solidsoft.refactor;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.assertj.core.api.Assertions;


public class U03_ExceptionTest {

	private static final String TEST_EXCEPTION_MESSAGE = "For test purpose only";

	/**
	 * Classic try..catch construction to test thrown exceptions (not recommended).
	 *
	 * DO NOT MODIFY - for anti-pattern presentation only. See the next method.
	 *
	 * Note: There are alternatives even in Java7: @Test(expected=...), ExpectedException, catch-exception
	 */
	@Test
	public void shouldVerifyThrownExceptionInOldFashionedJava7Way() {
		try {
			throwExceptionWithMessage(TEST_EXCEPTION_MESSAGE);
			fail("Excepting exception");
		} catch (Exception e) {
			assertThat(e)
					.isInstanceOf(NullPointerException.class)
					.hasMessage(TEST_EXCEPTION_MESSAGE);
		}
	}

	/**
	 * Capture exception thrown by throwExceptionWithMessage().
	 *
	 * Hint: Implement ExceptionCaptor.captureThrowable method to use lambda expressions.
	 * Hint 2: Change method signature if needed.
	 */
	@Test
	public void shouldCaptureThrowExceptionForFurtherAssertion() {
		//when
	    Throwable throwable = ExceptionCaptor.captureThrowable(() -> throwExceptionWithMessage(TEST_EXCEPTION_MESSAGE));
		//then
		assertThat(throwable)
				.isInstanceOf(NullPointerException.class)
				.hasMessage(TEST_EXCEPTION_MESSAGE);
	}

	/**
	 * Hint: Take a look at the methods in Assertions class.
	 */
    @Test
    public void shouldAssertThrowExceptionWithAssertJ3BuiltInSupport() {

        Assertions.assertThatThrownBy(() -> throwExceptionWithMessage(TEST_EXCEPTION_MESSAGE))
                .isInstanceOf(NullPointerException.class)
                .hasMessage(TEST_EXCEPTION_MESSAGE);
    }

	private void throwExceptionWithMessage(String message) {
		throw new NullPointerException(message);
	}
}
