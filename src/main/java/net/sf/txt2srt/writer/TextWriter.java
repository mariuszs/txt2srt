package net.sf.txt2srt.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import net.sf.txt2srt.Subtitle;
import net.sf.txt2srt.Subtitles;

abstract public class TextWriter extends SubtitlesWriter {
	public TextWriter(String type, String extension) {
		super(type, extension);
	}

	@Override
	public void writeSubtitles(OutputStream os, Subtitles subtitles)
			throws IOException {
		PrintWriter w = new PrintWriter(new OutputStreamWriter(os));
		
		Long prevStart = null;
		Subtitle prevSubtitle = null;
		for( Map.Entry<Long,Subtitle> entry : subtitles.getSubtitles().entrySet()) {
			Long start = entry.getKey();
			if (prevSubtitle!=null) {
				writeSubtitle(w, prevStart, Math.min(prevSubtitle.getDuration(),start-prevStart), prevSubtitle.getText());
			}
			prevStart = start;
			prevSubtitle = entry.getValue();
		}
		if (prevSubtitle!=null) {
			writeSubtitle(w, prevStart, prevSubtitle.getDuration(), prevSubtitle.getText());
		}
		w.flush();
	}

	abstract protected void writeSubtitle(PrintWriter w, long start, long duration, String text) throws IOException;

}
