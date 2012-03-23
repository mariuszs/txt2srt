package net.sf.txt2srt.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import net.sf.txt2srt.Subtitles;

public class SrtWriter extends TextWriter {
	protected int idx;
	
	public SrtWriter() {
		super("srt", "srt");
	}
	public void writeSubtitles(OutputStream os, Subtitles subtitles) throws IOException {
		idx = 1;
		super.writeSubtitles(os, subtitles);
	}

	@Override
	protected void writeSubtitle(PrintWriter w, long start, long duration, String text) throws IOException {
		w.println(idx);
		w.println(fmtTime(start)+" --> "+fmtTime(start+duration));
		w.println(text);
		w.println();
		idx++;
	}
	
	public String fmtTime(long v) {
		return String.format("%02d:%02d:%02d,%03d", v/3600000, (v/60000)%60, (v/1000)%60, v%1000 );
	}

}
