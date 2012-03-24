package net.sf.txt2srt;

public class SimpleOptions implements MovieParameters {

	String encoding;
	double framerate;
	long duration;

	public SimpleOptions(String encoding, double framerate, long duration) {
		super();
		this.encoding = encoding;
		this.framerate = framerate;
		this.duration = (duration == 0) ? 3000 : duration;
	}

	public String getEncoding() {
		return encoding;
	}

	public Double getSrcMovieFramerate() {
		return framerate;
	}

	public Long getDurationDefault() {
		return duration;
	}

}
