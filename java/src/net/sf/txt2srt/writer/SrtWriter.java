package net.sf.txt2srt.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import net.sf.txt2srt.Subtitle;
import net.sf.txt2srt.Subtitles;

public class SrtWriter extends SubtitlesWriter {

	public SrtWriter() {
		super("srt", "srt");
	}

	@Override
	public void write(OutputStream os, Subtitles subtitles) throws IOException {
		PrintWriter w = new PrintWriter(new OutputStreamWriter(os));
		
		int idx = 1;
		for( Map.Entry<Long,Subtitle> entry : subtitles.getSubtitles().entrySet()) {
			Subtitle subtitle = entry.getValue();
			long start = entry.getKey();
			long end = start+subtitle.getDuration();
			String text = subtitle.getText().trim();
			w.println(idx);
			w.println(fmtTime(start)+" --> "+fmtTime(end));
			w.println(text);
			w.println();
			idx++;
		}
		w.flush();
	}
	
	public String fmtTime(long v) {
		return String.format("%02d:%02d:%02d,%03d", v/3600000, (v/60000)%60, (v/1000)%60, v%1000 );
	}

}
