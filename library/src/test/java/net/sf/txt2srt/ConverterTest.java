package net.sf.txt2srt;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

	private static final int DURATION = 0;
	private static final int FRAMERATE = 25;
	private static final String ENCODING_CP1250 = "cp1250";
	private static final String SRC_TEST_RESOURCES_SAMPLE_TXT = "src/test/resources/sample.txt";
	private static final String SRC_TEST_RESOURCES_SAMPLE_SRT = "src/test/resources/sample.srt";
	private static final String TARGET_SAMPLE_SRT = "target/sample.srt";
	Converter conv;

	@Before
	public void setup() {
		conv = new Converter();
	}

	@Test
	public void clean() {
		File resultFile = new File(TARGET_SAMPLE_SRT);
		resultFile.delete();
		assertThat(!resultFile.exists()).isTrue();
	}

	@Test
	public void shouldReadTypeFromSpecifiedSubtitleFile()
			throws FileNotFoundException, IOException {
		conv.readSubtitle(SRC_TEST_RESOURCES_SAMPLE_TXT, ENCODING_CP1250,
				FRAMERATE, DURATION);

		assertThat(conv.getSubtitleType()).isNotEmpty();
		assertThat(conv.getSubtitleType()).isEqualTo("mpl2");

	}

	public void shouldWriteSubtitle()
			throws FileNotFoundException, IOException {
		conv.readSubtitle(SRC_TEST_RESOURCES_SAMPLE_TXT, ENCODING_CP1250,
				FRAMERATE, DURATION);

		String result = conv.writeSubtitle();
		File resultFile = new File(result);
		
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();

		// cleanup
		resultFile.delete();

	}

	@Test
	public void shouldCreateSrtSubtitle() throws IOException {

		conv.convert(SRC_TEST_RESOURCES_SAMPLE_TXT, TARGET_SAMPLE_SRT,
				ENCODING_CP1250, FRAMERATE, 0);

		File resultFile = new File(TARGET_SAMPLE_SRT);
		// File.
		assertThat(resultFile.isFile()).isTrue();
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();
	}

	@Test
	public void shouldCreateSrtSubtitleInThisSameDir() throws IOException {
		File resultFile = new File(SRC_TEST_RESOURCES_SAMPLE_SRT);
		assertThat(resultFile.isFile()).isFalse();
		
		conv = new Converter();

		String res = conv.convert(SRC_TEST_RESOURCES_SAMPLE_TXT,
				ENCODING_CP1250, FRAMERATE, 0);

		System.out.println("Result is " + res);
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();

		// cleanup
		resultFile.delete();
	}
}
