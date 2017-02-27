package info.solidsoft.nashorn;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class N01_HelloNashornTest {

	private static final String HELLO_WORLD_TEXT = "Hello from JavaScript!";
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Test
	public void shouldPrintHelloNashorn() throws ScriptException {
		//given
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");
		//when
		//engine.eval
		//then
		assertThat(systemOutRule.getLog()).isEqualToIgnoringWhitespace(HELLO_WORLD_TEXT);
	}
}
