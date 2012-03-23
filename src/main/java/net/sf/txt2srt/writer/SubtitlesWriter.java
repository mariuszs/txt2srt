package net.sf.txt2srt.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedHashMap;

import net.sf.txt2srt.Subtitles;

abstract public class SubtitlesWriter {
	protected String type;
	protected String extension;
	
	public SubtitlesWriter(String type, String extension) {
		super();
		this.type = type;
		this.extension = extension;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	abstract public void writeSubtitles(OutputStream os, Subtitles subtitles) throws IOException;


	// writers' registry
	static public SubtitlesWriter getWriter(String type) {
		return writers.get(type);
	}
	static public Collection<SubtitlesWriter> getWriters() {
		return writers.values();
	}
	static public void registerWriter(SubtitlesWriter w) {
		writers.put(w.getType(), w);
	}
	static protected LinkedHashMap<String, SubtitlesWriter> writers = new LinkedHashMap<String, SubtitlesWriter>();
	
	static {
		registerWriter(new SrtWriter());
	}
}
