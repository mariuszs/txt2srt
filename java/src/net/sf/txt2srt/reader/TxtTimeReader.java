package net.sf.txt2srt.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.txt2srt.Options;

public class TxtTimeReader extends LineTextReader {
	// static
	static protected Pattern reline = Pattern.compile("^(\\d+:\\d{2}:\\d{2})():(.*)$");
	static protected Pattern retime = Pattern.compile("(\\d+):(\\d{2}):(\\d{2})");
	
	public TxtTimeReader() {
		super("time");
	}
	
	protected long toTime(String s, Options options) {
		Matcher m = retime.matcher(s);
		m.find();
		long v = Long.parseLong(m.group(1))*3600+Long.parseLong(m.group(2))*60+Long.parseLong(m.group(3));
		v *= 1000;
		return v;
	}

	@Override
	protected Pattern getLinePattern() {
		return reline;
	}

}
