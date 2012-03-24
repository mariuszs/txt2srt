package net.sf.txt2srt;

public class Subtitle {
	protected String text;
	protected long duration; // duration in ms

	public Subtitle(String text, long duration) {
		this.text = text;
		this.duration = duration;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public String toString() {
		return "{"+duration+","+text+"}\n";
	}
}
