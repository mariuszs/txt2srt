package net.sf.txt2srt.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.txt2srt.Options;
import net.sf.txt2srt.Subtitles;

abstract public class LineTextReader extends PlainTextReader {
	public LineTextReader(String type) {
		super(type);
	}
	
	@Override
	public Subtitles read(Reader r, Options options) throws IOException {
		Subtitles subtitles = new Subtitles();
		BufferedReader br = new BufferedReader(r);
		String line;
		while((line=br.readLine())!=null) {
			if (line.trim().length()==0)
				continue;
			Matcher m = getLinePattern().matcher(line);
			// if wrong line try to ignore it
			if (!m.matches())
				throw new InvalidFormatException();
			long start = getStart(m.group(1),options);
			long duration = getDuration(start,m.group(2),options);
			String text = getText(m.group(3),options);
			subtitles.addSubtitle(start, text, duration);
		}
		
		return subtitles;
	}
	
	
	protected long getStart(String s, Options options) {
		return toTime(s, options);
	}
	protected long getDuration(long start, String s, Options options) {
		if (s==null || s.length()==0)
			return options.getDurationDefault();
		long end = toTime(s,options);
		return end-start;
	}
	protected String getText(String s, Options options) {
		return s.replace('|','\n');
	}

	abstract protected long toTime(String s, Options options);
	abstract protected Pattern getLinePattern();

}
