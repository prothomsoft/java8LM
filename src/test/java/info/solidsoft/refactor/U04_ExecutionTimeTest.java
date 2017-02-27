package info.solidsoft.refactor;

import org.assertj.core.api.WithAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.concurrent.locks.LockSupport;


public class U04_ExecutionTimeTest implements WithAssertions {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	/**
	 * Implement TimeExecutionLogger.log() which measures execution time of particular method (here executeForAtLeast()).
	 *
	 * Hint: Modify log() method execution if needed.
	 */
	@Test
	public void shouldLogMethodExecutionTime() {
		//given
		long durationInMillis = 100;
		
		//when
		TimeExecutionLogger.log(() -> LongExecutionFixture.executeForAtLeast(Duration.ofMillis(100)));

		//then
		String[] lines = stringToLines(systemOutRule.getLog());
		//and
		assertThat(lines).hasSize(2);
		assertThat(lines[0]).contains("100");
		assertThat(lines[1]).contains("execution took");
		//and
		String durationInLog = getPenultimateWord(lines[1]);
		assertThat(durationInLog).containsOnlyDigits();
		assertThat(Long.valueOf(durationInLog)).isGreaterThanOrEqualTo(durationInMillis);
	}

	private String getPenultimateWord(String line) {
		String[] words = line.split(" ");
		return words[words.length - 2];
	}

	private String[] stringToLines(String logs) {
		return logs.split(System.getProperty("line.separator"));
	}


	private static class LongExecutionFixture {

		private static void executeForAtLeast(Duration duration) {
			log.debug("Executing method with duration: {}", duration.toMillis());
			LockSupport.parkNanos(duration.toNanos());
		}
	}
}
