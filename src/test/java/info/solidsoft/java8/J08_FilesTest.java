package info.solidsoft.java8;

import info.solidsoft.java8.people.Person;
import info.solidsoft.java8.people.PersonDao;
import info.solidsoft.java8.people.Sex;

import org.junit.Test;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * - BufferedReader.lines()
 * - Comparator improvements
 */
public class J08_FilesTest {

	private final PersonDao dao = new PersonDao();

	@Test
	public void shouldLoadAllPeople() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();
		assertThat(people).hasSize(137);
	}

	@Test
	public void shouldSortByName() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		final List<String> names = people.stream()
		        .map(t -> t.getName())
		        .distinct()
		        //.sorted(Comparator.comparing(person -> person))
		        .sorted()
				.collect(toList());

		assertThat(names).startsWith("Aleksandar", "Alexander", "Alexandra", "Ali", "Alice");
	}

	/**
	 * Hint: Comparator.comparing()
	 */
	@Test
	public void shouldSortFemalesByHeightDesc() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		final List<String> names = people.stream()
		        .filter(person -> person.getSex() == Sex.FEMALE)
		        .sorted(Comparator.comparing((Function<Person, Integer>) (person) -> person.getHeight()).reversed())
				.map(t -> t.getName())
				.collect(toList());

		assertThat(names).startsWith("Mia", "Sevinj", "Anna", "Sofia");
	}

	@Test
	public void shouldSortByDateOfBirthWhenSameNames() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		final List<String> names = people.stream()
		        .sorted(Comparator.comparing((Function<Person, String>) (person) -> person.getName())
		                .thenComparing(person -> person.getDateOfBirth()))
		        .map(p -> p.getName() + '-' + p.getDateOfBirth().getYear())
		        .collect(toList());

		assertThat(names).startsWith("Aleksandar-1966", "Alexander-1986", "Alexander-1987", "Alexandra-1988", "Ali-1974");
	}

	/**
	 * @see Files#list(Path)
	 * @throws Exception
	 *
	 * Challenging
	 */
	@Test
	public void shouldGenerateStreamOfAllFilesIncludingSubdirectoriesRecursively() throws Exception {
		//given
		final String fileToSearch = J08_FilesTest.class.getSimpleName() + ".java";

		//when
		final Optional<Path> found = filesInDir(Paths.get("."))
				.filter(path -> path.endsWith(fileToSearch))
				.findAny();

		//then
		assertThat(found).isPresent();
	}

	private static Stream<Path> filesInDir(Path dir) {
		try {
            return Files.list(dir)
                    .flatMap(path -> Files.isDirectory(path) ? filesInDir(path) : Stream.of(path));
		    
		} catch (IOException e) {
		    throw Throwables.propagate(e);
		}

	}

}
