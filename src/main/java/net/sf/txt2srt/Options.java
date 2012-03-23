package net.sf.txt2srt;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Options {
	static public final String SRC = "src";
	static public final String DST = "dst";
	static public final String ENCODING = "encoding";
	static public final String FRAMERATE = "framerate";
	static public final String FRAMERATEFORCED = "framerateForced";
	static public final String DURATIONDEFAULT = "durationDefault";
	static public final String DSTTYPE = "dstType";
	static public final String SRCMOVIE = "srcMovie";
	
	protected Map<String, Object> values = new LinkedHashMap<String, Object>();
	protected Options parent;
	
	public Options(Options parent) {
		this.parent = parent;
	}
	public Options() {
		this(null);
	}

	public String getEncoding() {
		return (String)getValue(ENCODING);
	}
	public void setEncoding(String v) {
		setValue(ENCODING,v);
	}
	public Double getSrcMovieFramerate() {
		Double framerate = (Double)getValue(FRAMERATE);
		if (framerate==null) {
			framerate = findSrcMovieFramerate();
			setValue(FRAMERATE,framerate);
		}
		return framerate;
	}
	public Double getFramerateForced() {
		return (Double)getValue(FRAMERATEFORCED);
	}
	protected Double findSrcMovieFramerate() {
		Double framerate = getFramerateForced();
		if (framerate!=null)
			return framerate;
		
		String src = getSrc();
		String srcMovie = getSrcMovie();
		if (srcMovie==null)
			throw new RuntimeException("Cannot find matching movie for "+src+", so you have to set framerate manually");
		String[] args = {"mediainfo",srcMovie};
		try {
			StringBuilder sb = new StringBuilder();
			int retval = Util.exec(args, null, sb, null);
			String re = "\\s*Frame\\s+rate\\s*:\\s*(\\d+.?\\d*)";
			Matcher m = Pattern.compile(re,Pattern.CASE_INSENSITIVE).matcher(sb);
			if (!m.find()) {
				throw new RuntimeException("mediainfo cannot return info about framerate for movie "+srcMovie
						+"\nReturn code: "+retval
						+"\nOutput:"+sb);
			}
			framerate = Double.parseDouble(m.group(1));
			System.out.println("Got framerate "+framerate+" from "+srcMovie);
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
		return framerate;
	}

	public String getSrc() {
		return (String)getValue(SRC);
	}
	public void setSrc(String src) {
		setValue(SRC,src);
	}
	public String getSrcMovie() {
		String srcMovie = (String)getValue(SRCMOVIE);
		if (srcMovie==null) {
			// remove extension from path
			String src = getSrc();
			src = Util.removeExtension(src);
			String[] exts = {"avi","mkv","ogm","mp4","mov","ts"};
			for (String ext : exts) {
				File f = new File(src+"."+ext);
				if (f.exists()) {
					srcMovie = f.getPath();
					break;
				}
			}
			setValue(SRCMOVIE,srcMovie);
		}
		return srcMovie;
	}
	public void setSrcMovie(String srcMovie) {
		setValue(SRCMOVIE,srcMovie);
	}
	public String getDst() {
		return (String)getValue(DST);
	}
	public void setDst(String dst) {
		setValue(DST,dst);
	}
	public Long getDurationDefault() {
		Long durationDefault = (Long)getValue(DURATIONDEFAULT);
		if (durationDefault==null) {
			durationDefault = (long)3000;
			setValue(DURATIONDEFAULT,durationDefault);
		}
		return durationDefault;
	}
	public void setDurationDefault(Long durationDefault) {
		setValue(DURATIONDEFAULT, durationDefault);
	}

	public String getDstType() {
		String dstType = (String)getValue(DSTTYPE);
		if (dstType==null) {
			dstType = "srt";
			setValue(DSTTYPE, dstType);
		}
		return dstType;
	}
	public void setDstType(String dstType) {
		setValue(DSTTYPE, dstType);
	}

	protected Object getValue(String name) {
		if (!values.containsKey(name))
			return parent!=null?parent.getValue(name):null;
		return values.get(name);
	}
	protected void setValue(String name, Object v) {
		values.put(name,v);
	}
	
	public String getImplementationVersion() {
		return this.getClass().getPackage().getImplementationVersion();
	}
}
