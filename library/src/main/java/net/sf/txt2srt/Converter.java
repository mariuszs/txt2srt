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

	Subtitles subtitles = null;

	File sourceFile;
	
	String subtitleType;

	public String getSubtitleType() {
		return subtitleType;
	}

	public Converter() {
		super();
	}

	@Deprecated
	public String convert(final String source, final String encoding,
			final float framerate, final long duration) throws IOException {

		readSubtitle(source, encoding, framerate, duration);
		return writeSubtitle();
	}

	public void convert(final String source, final String destination,
			final String encoding, final float framerate, final long duration)
			throws IOException {

		readSubtitle(source, encoding, framerate, duration);
		writeSubtitle(destination);
	}

	public void readSubtitle(final String source, final String encoding,
			double framerate, long duration) throws FileNotFoundException,
			IOException {

		MovieParameters options = new SimpleOptions(encoding, framerate,
				duration);

		sourceFile = new File(source);

		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				sourceFile));

		is.mark(1024);

		for (SubtitlesReader r : readers) {
			try {
				is.reset();
				subtitles = r.read(is, options);
				System.out
						.println("Read from " + source + " as " + r.getType());
				subtitleType = r.getType();
				break;
			} catch (InvalidFormatException ex) {
			}
		}
	}

	public String writeSubtitle() throws FileNotFoundException, IOException {
		String outputFile = sourceFile.getName();
		if (outputFile.lastIndexOf('.') > -1) {
			outputFile = sourceFile.getPath().substring(0,
					sourceFile.getPath().lastIndexOf('.'));
		}
		String destination = outputFile + ".srt";
		writeSubtitle(destination);
		return destination;
	}

	public void writeSubtitle(final String destination)
			throws FileNotFoundException, IOException {
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
