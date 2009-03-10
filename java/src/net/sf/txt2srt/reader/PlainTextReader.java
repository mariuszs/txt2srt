package net.sf.txt2srt.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.sf.txt2srt.Options;
import net.sf.txt2srt.Subtitles;

abstract public class PlainTextReader extends SubtitlesReader {

	public PlainTextReader(String type) {
		super(type);
	}
	@Override
	public Subtitles read(InputStream is, Options options) throws IOException {
		String encoding = options.getEncoding();
		InputStreamReader r = encoding!=null && encoding.length()>0?new InputStreamReader(is,encoding):new InputStreamReader(is);
		return read(r,options);
	}
	abstract public Subtitles read(Reader r, Options options) throws IOException;
}
