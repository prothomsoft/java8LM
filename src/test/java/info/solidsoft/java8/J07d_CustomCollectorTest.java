package info.solidsoft.java8;

import com.google.common.collect.ImmutableSet;
import info.solidsoft.java8.util.StreamTestFixture;
import info.solidsoft.java8.util.StreamTestFixture.StreamParallelism;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static info.solidsoft.java8.util.StreamTestFixture.PARALLEL_TEST_CASE_NAME_FORMAT;
import static info.solidsoft.java8.util.StreamTestFixture.changeStreamParallelism;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implement custom Collector to ImmutableSet from Guava.
 *
 * Hint: Use ImmutableSet.Builder to keep mutable, accumulating state.
 *
 * @see ImmutableSet#builder()
 */
@Ignore
@RunWith(JUnitParamsRunner.class)
public class J07d_CustomCollectorTest {

	@Test
	public void shouldReturnEmptyImmutableSet() throws Exception {
		//given
		final Set<Integer> items = Collections.emptySet();

		//when
		final ImmutableSet<Integer> set = null; //items.stream().collect(new ImmutableSetCollector<>());

		//then
		assertThat(set).isEmpty();
	}

	@Test
	public void shouldReturnImmutableSetWithJustOneElement() throws Exception {
		//given
		final List<Integer> items = Collections.singletonList(42);

		//when
		final ImmutableSet<Integer> set = null; //items.stream().collect(new ImmutableSetCollector<>());

		//then
		assertThat(set).containsExactly(42);
	}

	@Test
	@Parameters(source = StreamTestFixture.class)
	@TestCaseName(PARALLEL_TEST_CASE_NAME_FORMAT)
	public void shouldCollectToImmutableSet(StreamParallelism requestedParallelism) throws Exception {
		//given
		final List<Integer> items = Arrays.asList(3, 5, 2, 4, 7, 5, 3, 9, 2);
		Stream<Integer> stream = changeStreamParallelism(items.stream(), requestedParallelism);

		//when
		final ImmutableSet<Integer> set = null; //stream.collect(new ImmutableSetCollector<>());

		//then
		assertThat(set).containsOnly(2, 3, 4, 5, 7, 9);
	}

	@Test
	public void shouldWorkInConcurrentEnvironment() throws Exception {
		//given
		final Stream<Long> longsWithDuplicates = LongStream
				.range(0, 100_000)
				.parallel()
				.mapToObj(x -> x / 2);

		//when
		final ImmutableSet<Long> set = null; //longsWithDuplicates.collect(new ImmutableSetCollector<>());

		//then
		final Set<Long> expected = LongStream
				.range(0, 100_000 / 2)
				.mapToObj(Long::valueOf)
				.collect(toSet());
		assertThat(set).isEqualTo(expected);
	}

}
