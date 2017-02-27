package info.solidsoft.java8.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public interface Encrypter {
	byte[] encode(byte[] bytes);

	default public byte[] encode(String str, Charset charset) {
	    return encode(str.getBytes(charset));
	};

	default public byte[] encode(char[] chars, Charset charset) {
	    return encode(String.valueOf(chars), charset);
	}

	default public byte[] encode(Reader reader, Charset charset) throws IOException {
	    return encode(IOUtils.toByteArray(reader, charset));
	}

	default public byte[] encode(InputStream is) throws IOException {
	    return encode(IOUtils.toByteArray(is));	    
	}
}
