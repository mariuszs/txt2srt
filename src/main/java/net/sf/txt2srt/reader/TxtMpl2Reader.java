package net.sf.txt2srt.reader;

import java.util.regex.Pattern;

import net.sf.txt2srt.Options;

public class TxtMpl2Reader extends LineTextReader {
	// static
	static protected Pattern reline = Pattern.compile("^\\[(\\d+)\\]\\[(\\d+)\\](.*)$");
	
	public TxtMpl2Reader() {
		super("mpl2");
	}

	@Override
	protected long getDuration(long start, String s, Options options) {
		return super.getDuration(start, s, options);
	}
	protected long toTime(String s, Options options) {
		long time = Long.parseLong(s);
		return time*100;
	}

	@Override
	protected Pattern getLinePattern() {
		return reline;
	}

}
