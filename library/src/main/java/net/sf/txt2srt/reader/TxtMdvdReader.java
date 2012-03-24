package net.sf.txt2srt.reader;

import java.util.regex.Pattern;

import net.sf.txt2srt.MovieParameters;

public class TxtMdvdReader extends LineTextReader {
	// static
	static protected Pattern reline = Pattern.compile("^\\{(\\d+)\\}\\{(\\d+)\\}(.*)$");
	
	public TxtMdvdReader() {
		super("mdvd");
	}

	@Override
	protected long getDuration(long start, String s, MovieParameters options) {
		return super.getDuration(start, s, options);
	}
	protected long toTime(String s, MovieParameters options) {
		long frame = Long.parseLong(s);
		double framerate = options.getSrcMovieFramerate();
		long v = (long)(1000*frame/framerate);
		return v;
	}

	@Override
	protected Pattern getLinePattern() {
		return reline;
	}

}
