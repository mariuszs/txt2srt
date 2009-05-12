package net.sf.txt2srt.reader;

import java.io.IOException;

public class InvalidFormatException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidFormatException(String msg) {
		super(msg);
	}
}
