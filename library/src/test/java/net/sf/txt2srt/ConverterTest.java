package net.sf.txt2srt;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class ConverterTest {

	private static final String TARGET_SAMPLE_SRT = "target/sample.srt";

	@Test
	public void clean() {
		File resultFile = new File(TARGET_SAMPLE_SRT);
		resultFile.delete();
		assertThat(!resultFile.exists()).isTrue();
	}

	@Test
	public void testConvertFile() throws IOException {
		String file = "src/test/resources/sample.txt";

		Converter conv = new Converter();

		conv.convert(file, TARGET_SAMPLE_SRT, "cp1250", 25, 0);

		File resultFile = new File(TARGET_SAMPLE_SRT);
		// File.
		assertThat(resultFile.isFile()).isTrue();
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();
	}

	@Test
	public void testConvertFileWithoutDest() throws IOException {
		String file = "src/test/resources/sample.txt";

		Converter conv = new Converter();

		String res = conv.convert(file, "cp1250", 25, 0);

		System.out.println("Result is " + res);
		File resultFile = new File(TARGET_SAMPLE_SRT);
		// File.
		assertThat(resultFile.isFile()).isTrue();
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();
	}
}
