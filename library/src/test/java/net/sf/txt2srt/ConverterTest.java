package net.sf.txt2srt;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

	private static final int DURATION = 0;
	private static final int FRAMERATE = 25;
	private static final String ENCODING_CP1250 = "cp1250";
	private static final String INPUT_SAMPLE_SUBTITLE_MPL2 = "src/test/resources/sample.txt";
	private static final String CONVERTED_SAMPLE_SUBTITLE_SRT = "src/test/resources/sample.srt";
	private static final String CONVERTED_SAMPLE_SUBTITLE_SRT_TARGET = "target/sample.srt";

	Converter conv;

	private File resultFile;

	@Before
	public void setup() {
		resultFile = new File(CONVERTED_SAMPLE_SUBTITLE_SRT);
		cleanup();
		assertThat(resultFile.isFile()).isFalse();
		conv = new Converter();
	}

	@After
	public void cleanup() {
		if (resultFile != null && resultFile.exists()) {
			resultFile.delete();
		}
	}

	@Test
	public void shouldReadTypeFromSpecifiedSubtitleFile()
			throws FileNotFoundException, IOException {
		conv.readSubtitle(INPUT_SAMPLE_SUBTITLE_MPL2, ENCODING_CP1250,
				FRAMERATE, DURATION);

		assertThat(conv.getSubtitleType()).isNotEmpty();
		assertThat(conv.getSubtitleType()).isEqualTo("mpl2");

	}

	public void shouldWriteSubtitle() throws FileNotFoundException, IOException {

		conv.readSubtitle(INPUT_SAMPLE_SUBTITLE_MPL2, ENCODING_CP1250,
				FRAMERATE, DURATION);

		String result = conv.writeSubtitle();

		assertThat(result).isEqualTo(resultFile.getPath());
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();

	}

	@Test
	public void shouldCreateSrtSubtitle() throws IOException {

		conv.convert(INPUT_SAMPLE_SUBTITLE_MPL2,
				CONVERTED_SAMPLE_SUBTITLE_SRT_TARGET, ENCODING_CP1250,
				FRAMERATE, 0);

		resultFile = new File(CONVERTED_SAMPLE_SUBTITLE_SRT_TARGET);

		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();
	}

	@Test
	public void shouldCreateSrtSubtitleInThisSameDir() throws IOException {

		conv = new Converter();

		String result = conv.convert(INPUT_SAMPLE_SUBTITLE_MPL2,
				ENCODING_CP1250, FRAMERATE, 0);

		System.out.println("Result is " + result);
		assertThat(result).isEqualTo(resultFile.getPath());
		assertThat(resultFile.exists() && resultFile.isFile()).isTrue();

	}
}
