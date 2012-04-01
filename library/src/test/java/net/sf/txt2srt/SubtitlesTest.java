package net.sf.txt2srt;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class SubtitlesTest {

	private static final String SUBTITLE_TYPE = "xxx";
	Subtitles subtitles;

	@Before
	public void setUp() throws Exception {
		subtitles = new Subtitles(SUBTITLE_TYPE);
	}

	@Test
	public void shouldGetSubtitlesSize() {
		subtitles.addSubtitle(0, "test", 1);
		subtitles.addSubtitle(2, "test", 3);
		subtitles.addSubtitle(4, "test", 5);

		assertThat(subtitles.size()).isEqualTo(3);
	}

	@Test
	public void shouldGetSubtitlesSourceType() {

		assertThat(subtitles.getSourceFormat()).isEqualTo(SUBTITLE_TYPE);
	}

}
