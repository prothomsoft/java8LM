package info.solidsoft.nashorn.util;

/**
 * To present a Java object mutation posibility in JavaScript.
 *
 * The design of this class could be enhanced in the other case.
 */
public class Mutable {
	private String someField;

	public Mutable(String someField) {
		this.someField = someField;
	}

	public String getSomeField() {
		return someField;
	}

	public void setSomeField(String someField) {
		this.someField = someField;
	}
}