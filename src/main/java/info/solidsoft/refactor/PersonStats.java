package info.solidsoft.refactor;

import info.solidsoft.java8.people.Person;
import info.solidsoft.java8.people.PersonDao;
import info.solidsoft.java8.people.Sex;

import java.util.List;
import java.util.function.ToLongFunction;

/**
 * 1. Simplify for constructions with Stream API.
 * 2. How use a function can help to reduce duplication?
 */
public class PersonStats {

	private final PersonDao personDao;

	public PersonStats(PersonDao personDao) {
		this.personDao = personDao;
	}

	public long countPeopleWithGivenSex(Sex requestedSex) {
		List<Person> people = personDao.loadPeopleDatabase();
		return people.stream()
		.filter(person -> person.getSex().equals(requestedSex))
		.count();
	}

	public long calculateTotalWeight() {
		List<Person> people = personDao.loadPeopleDatabase();
		long sum = getSum(people, person -> person.getWeight());		
		return sum;
	}

	public long calculateTotalHeight() {
		List<Person> people = personDao.loadPeopleDatabase();
		long sum = getSum(people, person -> person.getHeight());		
		return sum;
	}
	
	private long getSum(List<Person> people, ToLongFunction<Person> person) {
        long sum = people.stream().mapToLong(person).sum();
        return sum;
    }
}
