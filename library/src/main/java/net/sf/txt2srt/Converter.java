package net.sf.txt2srt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import net.sf.txt2srt.reader.InvalidFormatException;
import net.sf.txt2srt.reader.SubtitlesReader;
import net.sf.txt2srt.writer.SubtitlesWriter;

public class Converter {

	Collection<SubtitlesReader> readers = SubtitlesReader.getReaders();

	public Converter() {
		super();
	}

	@Deprecated
	public String convert(final String source, final String encoding,
			final float framerate, final long duration) throws IOException {

		Subtitles subtitles = readSubtitle(source, encoding, framerate,
				duration);
		return writeSubtitle(subtitles);
	}

	public void convert(final String source, final String destination,
			final String encoding, final float framerate, final long duration)
			throws IOException {

		Subtitles subtitles = readSubtitle(source, encoding, framerate,
				duration);
		writeSubtitle(subtitles, destination);
	}

	public Subtitles readSubtitle(final String source, final String encoding,
			double framerate, long duration) throws FileNotFoundException,
			IOException {

		Subtitles subtitles = null;

		MovieParameters options = new SimpleOptions(encoding, framerate,
				duration);

		File sourceFile = new File(source);

		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				sourceFile));

		is.mark(1024);

		for (SubtitlesReader r : readers) {
			try {
				is.reset();
				subtitles = r.read(is, options);
				System.out
						.println("Read from " + source + " as " + r.getType());
				break;
			} catch (InvalidFormatException ex) {
			}
		}

		subtitles.setSourceFilePath(source);

		return subtitles;
	}

	public String writeSubtitle(final Subtitles subtitles)
			throws FileNotFoundException, IOException {
		File sourceFile = new File(subtitles.getSourceFilePath());
		String outputFile = sourceFile.getName();
		if (outputFile.lastIndexOf('.') > -1) {
			outputFile = sourceFile.getPath().substring(0,
					sourceFile.getPath().lastIndexOf('.'));
		}
		String destination = outputFile + ".srt";
		writeSubtitle(subtitles, destination);
		return destination;
	}

	public void writeSubtitle(final Subtitles subtitles,
			final String destination) throws FileNotFoundException, IOException {

		SubtitlesWriter writer = SubtitlesWriter.getWriter("srt");

		if (subtitles != null) {
			FileOutputStream os = new FileOutputStream(destination);
			writer.writeSubtitles(os, subtitles);
			os.close();
			System.out.println("Written to " + destination + " as "
					+ writer.getType());
		}
	}

}
