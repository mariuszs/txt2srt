package net.sf.txt2srt.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;

import net.sf.txt2srt.Options;
import net.sf.txt2srt.Subtitles;

abstract public class SubtitlesReader {
	protected String type;
	
	public SubtitlesReader(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	abstract public Subtitles read(InputStream is, Options options) throws IOException;
	
	
	// readers' registry
	static public SubtitlesReader getReader(String type) {
		return readers.get(type);
	}
	static public Collection<SubtitlesReader> getReaders() {
		return readers.values();
	}
	static public void registerReader(SubtitlesReader r) {
		readers.put(r.getType(), r);
	}
	static protected LinkedHashMap<String, SubtitlesReader> readers = new LinkedHashMap<String, SubtitlesReader>();
	
	static {
		registerReader(new TxtMpl2Reader());
		registerReader(new TxtTimeReader());
		registerReader(new TxtMdvdReader());
	}
}
