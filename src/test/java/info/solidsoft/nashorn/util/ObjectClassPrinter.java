package info.solidsoft.nashorn.util;

public class ObjectClassPrinter {

	public static void print(Object object) {
		System.out.println("Argument class: " + object.getClass().getName());
	}
}
