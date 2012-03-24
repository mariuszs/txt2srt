package net.sf.txt2srt.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.sf.txt2srt.MovieParameters;
import net.sf.txt2srt.Subtitles;

abstract public class PlainTextReader extends SubtitlesReader {

	public PlainTextReader(String type) {
		super(type);
	}
	@Override
	public Subtitles read(InputStream is, MovieParameters options) throws IOException {
		String encoding = options.getEncoding();
		InputStreamReader r = encoding!=null && encoding.length()>0?new InputStreamReader(is,encoding):new InputStreamReader(is);
		return read(r,options);
	}
	abstract public Subtitles read(Reader r, MovieParameters options) throws IOException;
}
