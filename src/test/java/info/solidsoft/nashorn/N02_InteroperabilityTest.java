package info.solidsoft.nashorn;

import info.solidsoft.java8.people.Person;
import info.solidsoft.java8.people.PersonDao;
import info.solidsoft.nashorn.util.Mutable;
import org.junit.Ignore;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class N02_InteroperabilityTest {

	private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	private Invocable invocable = (Invocable) engine;

	@Test
	public void shouldUseExchangeSimpleObjectsBetweenJavaAndJS() throws Exception {
		//given
		readScriptFromClasspathAndEvaluate("/nashorn/whoAreYou.js");
		//and
		Invocable invocable = (Invocable) engine;
		//and
		String questioner = "Java8";
		//when
		Object result = null;   //invocable.invokeFunction
		//then
		assertThat(result)
				.isInstanceOf(String.class)
				.isEqualTo("I'm Nashorn, Java8");
	}

	@Test
	public void shouldAllowToPassComplexObjectsToJS() throws Exception {
		//given
		readScriptFromClasspathAndEvaluate("/nashorn/objectName.js");
		//when
		Object result = null;   //invocable.invokeFunction
		//then
		assertThat(result)
				.isInstanceOf(String.class)
				.isEqualTo("java.util.Date");
	}

	@Test
	public void jsObjectRepresentationInJava() throws Exception {
		readScriptFromClasspathAndEvaluate("/nashorn/objectNameInJava.js");

		invocable.invokeFunction("callJavaClassPrinter");

		//Pay attention how custom Java classes have to be referenced to be usable from JavaScript

		//Play with different objects created in JavaScript
	}

	@Test
	public void shouldModifyJavaObjectInJS() throws Exception {
		//given
		readScriptFromClasspathAndEvaluate("/nashorn/modifyObject.js");
		//and
		Mutable mutable = new Mutable("initial");
		//when
		invocable.invokeFunction("modifyObjectWithValue", mutable, "newValue"); //write code in modifyObject.js
		//then
		assertThat(mutable.getSomeField()).isEqualTo("newValue");;
	}

	@Test
	public void shouldAllowToUseStreamsInJS() throws Exception {
		//given
		readScriptFromClasspathAndEvaluate("/nashorn/streams.js");
		//and
		List<Person> people = new PersonDao().loadPeopleDatabase();
		int minimalHeight = 170;
		//when
		Object result = null;   //invocable.invokeFunction + write code in JS
		//then
		assertThat(result)
				.isInstanceOf(Long.class)
				.isEqualTo(27L);

	}

	private void readScriptFromClasspathAndEvaluate(String scriptName) throws ScriptException, FileNotFoundException {
		String scriptPath = getClass().getResource(scriptName).getFile();
		engine.eval(new FileReader(scriptPath));
	}
}
