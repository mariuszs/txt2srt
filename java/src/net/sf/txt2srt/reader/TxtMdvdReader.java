package net.sf.txt2srt.reader;

import java.util.regex.Pattern;

import net.sf.txt2srt.Options;

public class TxtMdvdReader extends LineTextReader {
	// static
	static protected Pattern reline = Pattern.compile("^\\{(\\d+)\\}\\{(\\d+)\\}(.*)$");
	
	public TxtMdvdReader() {
		super("mdvd");
	}

	@Override
	protected long getDuration(long start, String s, Options options) {
		return super.getDuration(start, s, options);
	}
	protected long toTime(String s, Options options) {
		long frame = Long.parseLong(s);
		double framerate = options.getFramerate();
		long v = (long)(1000*frame/framerate);
		return v;
	}

	@Override
	protected Pattern getLinePattern() {
		return reline;
	}

}
