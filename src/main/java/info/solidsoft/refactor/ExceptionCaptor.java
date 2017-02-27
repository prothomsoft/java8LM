package info.solidsoft.refactor;

public class ExceptionCaptor {

	public static Throwable captureThrowable(Runnable runnableException) {
	    try {
	        runnableException.run();
        } catch (Exception e) {
            return e;
        }
	    return null;
	}
}
