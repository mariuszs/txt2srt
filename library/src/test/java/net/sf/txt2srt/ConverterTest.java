package net.sf.txt2srt;

import java.io.File;
import java.io.IOException;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class ConverterTest {

	private static final String TARGET_SAMPLE_SRT = "target/sample.srt";

	public void clean() {
		File resultFile = new File(TARGET_SAMPLE_SRT);
		resultFile.delete();
		Assertions.assertThat(!resultFile.exists());
	}

	@Test
	public void testConvertFile() throws IOException {
		String file = "src/test/resources/sample.txt";

		Converter conv = new Converter();

		conv.convert(file, TARGET_SAMPLE_SRT, "cp1250", 25, 0);

		File resultFile = new File(TARGET_SAMPLE_SRT);

		Assertions.assertThat(resultFile.exists() && resultFile.isFile());
	}
}
