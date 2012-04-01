package net.sf.txt2srt;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Subtitles {

	private String sourceType;

	public Subtitles(String sourceType) {
		super();
		this.sourceType = sourceType;
	}

	// maps start time in ms to single subtitle
	protected TreeMap<Long, Subtitle> subtitles = new TreeMap<Long, Subtitle>();

	public void addSubtitle(long start, String text, long duration) {
		subtitles.put(start, new Subtitle(text, duration));
	}

	public Map<Long, Subtitle> getSubtitles() {
		return Collections.unmodifiableMap(subtitles);
	}

	public int size() {
		return subtitles.size();
	}

	@Override
	public String toString() {
		return subtitles.toString();
	}

	public String getSourceType() {
		return sourceType;
	}
}
