package net.sf.txt2srt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import net.sf.txt2srt.reader.InvalidFormatException;
import net.sf.txt2srt.reader.SubtitlesReader;
import net.sf.txt2srt.writer.SubtitlesWriter;

public class Txt2Srt {
	
	public void convert(String[] srcs, Options options) throws IOException {
		if (srcs==null || srcs.length==0)
			syntax(0,options);
		
		for(String src : srcs) {
			options.setSrc(src);
			convert(options);
		}
	}
	public void convert(Options options) throws IOException {
		String dstType = options.getDstType();
		SubtitlesWriter w = SubtitlesWriter.getWriter(dstType);
		if (w==null)
			throw new RuntimeException("Cannot find writer for type "+dstType);
		
		String src = options.getSrc();
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(src));
		
		Collection<SubtitlesReader> readers = SubtitlesReader.getReaders();
		is.mark(1024);
		Subtitles subtitles = null;
		for (SubtitlesReader r : readers) {
			try {
				is.reset();
				subtitles = r.read(is, options);
				System.out.println("Read from "+src+" as "+r.getType());
				break;
			} catch(InvalidFormatException ex) {
			}
		}
		if (subtitles!=null) {
			String dst = options.getDst();
			if (dst==null) {
				dst = Util.removeExtension(options.getSrc())+"."+w.getExtension();
			}
			FileOutputStream os = new FileOutputStream(dst);
			w.writeSubtitles(os, subtitles);
			os.close();
			System.out.println("Written to "+dst+" as "+w.getType());
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Txt2Srt().convert(args,new Options());
	}
	
	public static void syntax(Integer exitCode, Options options) {
		System.out.println("txt2srt version "+options.getImplementationVersion());
		System.out.println("Usage:");
		System.out.println("\tjava -jar txt2srt.jar file1 [file2 [...]]");
		if (exitCode!=null)
			System.exit(exitCode);
	}
}
