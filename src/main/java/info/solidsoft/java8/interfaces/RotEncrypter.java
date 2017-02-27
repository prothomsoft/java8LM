package info.solidsoft.java8.interfaces;

public class RotEncrypter implements Encrypter {

	@Override
	public byte[] encode(byte[] bytes) {
		final byte[] result = new byte[bytes.length];
		for (int i = 0; i < bytes.length; ++i) {
			result[i] = (byte) (bytes[i] + 13);
		}
		return result;
	}
}
