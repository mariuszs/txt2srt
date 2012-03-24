package net.sf.txt2srt.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.txt2srt.MovieParameters;
import net.sf.txt2srt.Subtitles;

abstract public class LineTextReader extends PlainTextReader {
	public LineTextReader(String type) {
		super(type);
	}
	
	@Override
	public Subtitles read(Reader r, MovieParameters options) throws IOException {
		Subtitles subtitles = new Subtitles();
		BufferedReader br = new BufferedReader(r,1024);
		String line; int lineno = 0; int reallineno = 0; 
		while((line=br.readLine())!=null) {
			lineno++;
			if (line.trim().length()==0)
				continue;
			reallineno++;
			Matcher m = getLinePattern().matcher(line);
			// if wrong line try to ignore it
			if (!m.matches()) {
				String msg = "Invalid format at line "+lineno;
				if (reallineno>1) {
					System.err.println(msg+". Ignoring: '"+line+"'");
					continue;
				}
				throw new InvalidFormatException(msg);
			}
			long start = getStart(m.group(1),options);
			long duration = getDuration(start,m.group(2),options);
			String text = getText(m.group(3),options);
			subtitles.addSubtitle(start, text, duration);
		}
		
		return subtitles;
	}
	
	
	protected long getStart(String s, MovieParameters options) {
		return toTime(s, options);
	}
	protected long getDuration(long start, String s, MovieParameters options) {
		if (s==null || s.length()==0)
			return options.getDurationDefault();
		long end = toTime(s,options);
		return end-start;
	}
	protected String getText(String s, MovieParameters options) {
		return s.replace('|','\n');
	}

	abstract protected long toTime(String s, MovieParameters options);
	abstract protected Pattern getLinePattern();

}
