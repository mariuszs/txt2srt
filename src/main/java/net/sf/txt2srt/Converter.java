package net.sf.txt2srt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import net.sf.txt2srt.reader.InvalidFormatException;
import net.sf.txt2srt.reader.SubtitlesReader;
import net.sf.txt2srt.writer.SubtitlesWriter;

public class Converter {

	public void convert(String source, String destination, String encoding, double framerate, long duration) throws IOException {
		MovieParameters options = new SimpleOptions(encoding, framerate, duration);
		// TODO Auto-generated method stub
		SubtitlesWriter w = SubtitlesWriter.getWriter("srt");

		BufferedInputStream is = new BufferedInputStream(new FileInputStream(source));
		Collection<SubtitlesReader> readers = SubtitlesReader.getReaders();
		is.mark(1024);
		Subtitles subtitles = null;
		for (SubtitlesReader r : readers) {
			try {
				is.reset();
				subtitles = r.read(is, options);
				System.out.println("Read from " + source + " as " + r.getType());
				break;
			} catch (InvalidFormatException ex) {
			}
		}
		if (subtitles != null) {
			// if (dst == null) {
			// dst = Util.removeExtension(options.getSrc()) + "." +
			// w.getExtension();
			// }
			FileOutputStream os = new FileOutputStream(destination);
			w.writeSubtitles(os, subtitles);
			os.close();
			System.out.println("Written to " + destination + " as " + w.getType());
		}
	}

}
