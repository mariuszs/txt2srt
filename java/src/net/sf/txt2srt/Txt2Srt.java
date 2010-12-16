package net.sf.txt2srt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.txt2srt.reader.InvalidFormatException;
import net.sf.txt2srt.reader.SubtitlesReader;
import net.sf.txt2srt.writer.SubtitlesWriter;

public class Txt2Srt {
	static public final String[] MOVIE_EXTS = {".avi",".mkv"};
	
	public void convert(String[] srcs, Options options) throws IOException {
		if (srcs==null || srcs.length==0)
			syntax(0,options);
		
		for(String src : srcs) {
			File f = new File(src);
			if (f.isDirectory()) {
				Map<String,File> txts = Util.list(f, null, Pattern.compile("\\.txt$"));
				if (txts.isEmpty())
					continue;
				ArrayList<String> srcs2 = new ArrayList<String>();
				for(File f2 : txts.values()) {
					String s2 = f2.getPath();
					if (checkAlternateFileExtension(s2.substring(0,s2.length()-4),MOVIE_EXTS)!=null)
						srcs2.add(s2);
				}
				convert( srcs2.toArray(new String[srcs2.size()]), options);
			} else {
				Options srcOptions = new Options(options);
				srcOptions.setSrc(src);
				convert(srcOptions);
			}
		}
	}
	private File checkAlternateFileExtension(String prefix, String[] exts) {
		for(String ext : exts) {
			File f = new File(prefix+ext);
			if (f.exists() && f.isFile())
				return f;
		}
		return null;
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
