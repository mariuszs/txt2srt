package net.sf.txt2srt;

import java.io.IOException;

import org.junit.Test;

public class ConverterTest {

	@Test
	public void testConvertFile() throws IOException {
		String file = "src/test/resources/sample.txt";

		Converter conv = new Converter();

		conv.convert(file, "target/sample.srt", "cp1250", 25, 0);

	}
}
