package info.solidsoft.refactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

public class TimeExecutionLogger {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * Hint: StopWatch from Guava can be used to measure execution time.
	 * @param object 
	 */
	public static void log(Runnable object) {
	    
	    Stopwatch createStarted = Stopwatch.createStarted();
	    object.run();
	    long elapsed = createStarted.elapsed(TimeUnit.MILLISECONDS);
	    log.debug("execution took " + elapsed + " ms");
		
	}
}
