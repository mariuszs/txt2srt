package net.sf.txt2srt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Util {
	static public int exec(String[] cmdarray, File dir, StringBuilder output, String encoding) throws IOException {
		Process p = new ProcessBuilder(cmdarray)
			.directory(dir)
			.redirectErrorStream(true)
			.start();

		p.getOutputStream().close();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[2048];
		int c;
		InputStream is = p.getInputStream();
		while((c=is.read(buf))!=-1) {
			if (output!=null)
				bos.write(buf,0,c);
		}
		p.getInputStream().close();
		
		if (output!=null)
			output.append( encoding!=null?new String(bos.toByteArray(),encoding):new String(bos.toByteArray()) );
		
		return p.exitValue();
	}
	
	static public String removeExtension(String p) {
		int idxDot = p.lastIndexOf('.');
		return p.substring(0,idxDot);
	}
	
	static public Map<String,File> list(File file, String path, Pattern filter) {
		return list(file, path, filter, null);
	}
	static public Map<String,File> list(File file, String path, Pattern filter, Map<String,File> dst) {
		if (dst==null)
			dst = new LinkedHashMap<String, File>();
		if (file.isDirectory()) {
			if (path==null || path.length()==0)
				path = "";
			else if (!path.endsWith("/"))
				path += "/";
			if (filter==null || filter.matcher(path).find())
				dst.put(path, file);
			String[] list = file.list();
			for(String s : list) {
				if (s=="." || s=="..")
					continue;
				list( new File(file,s), path+s, filter, dst );
			}
		} else {
			if (path==null || path.length()==0)
				path = file.getName();
			if (filter==null || filter.matcher(path).find())
				dst.put(path, file);
		}
		return dst;
	}
}
