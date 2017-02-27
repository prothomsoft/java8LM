package info.solidsoft.refactor;

import info.solidsoft.java8.people.PersonDao;
import info.solidsoft.java8.people.Sex;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Refactor PersonStats class.
 */
public class U02_CollectionTest {

	private PersonStats personStats = new PersonStats(new PersonDao());

	@Test
	public void shouldCountPeopleWithGivenSex() {
		//when
		long females = personStats.countPeopleWithGivenSex(Sex.FEMALE);
		//then
		assertThat(females).isEqualTo(68);
	}

	@Test
	public void shouldCalculateTotalWeight() {
		//when
		long totalWeight = personStats.calculateTotalWeight();
		//then
		assertThat(totalWeight).isEqualTo(8998);
	}

	@Test
	public void shouldCalculateTotalHeight() {
		//when
		long totalHeight = personStats.calculateTotalHeight();
		//then
		assertThat(totalHeight).isEqualTo(23837);
	}
}
