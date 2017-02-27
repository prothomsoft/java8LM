package info.solidsoft.java8.atomic;

/**
 * Keeps minimal range including all saved numbers.
 *
 * Example. Saving values: 4, 10, -3, 2 - minimal range - from -3 to 10.
 */
public class RangeCollector {

	public void save(double x) {
		throw new UnsupportedOperationException("save()");
	}

	public double getMin() {
		throw new UnsupportedOperationException("getMin()");
	}

	public double getMax() {
		throw new UnsupportedOperationException("getMax()");
	}

}
