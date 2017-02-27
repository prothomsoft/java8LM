package info.solidsoft.java8.atomic;

public class EventCounter extends Number {

	public void incBy(long x) {
		throw new UnsupportedOperationException("incBy()");
	}

	public long reset() {
		throw new UnsupportedOperationException("reset()");
	}

	@Override
	public int intValue() {
		throw new UnsupportedOperationException("intValue()");
	}

	@Override
	public long longValue() {
		throw new UnsupportedOperationException("longValue()");
	}

	@Override
	public float floatValue() {
		throw new UnsupportedOperationException("floatValue()");
	}

	@Override
	public double doubleValue() {
		throw new UnsupportedOperationException("doubleValue()");
	}
}
