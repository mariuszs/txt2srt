package net.sf.txt2srt;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

public class CharsetTest {
	@Test
	public void t1() {
		Map<String, Charset> charSets = Charset.availableCharsets();
		Iterator<String> it = charSets.keySet().iterator();
		while (it.hasNext()) {
			String csName = (String) it.next();
			System.out.println(csName);

			Charset charset = charSets.get(csName);
			System.out.println(charset.displayName() + "\n");
			
			Iterator aliases = ((Charset) charSets.get(csName)).aliases()
					.iterator();
			if (aliases.hasNext())
				System.out.print(": ");
			while (aliases.hasNext()) {
				System.out.print(aliases.next());
				if (aliases.hasNext())
					System.out.print(", ");
		}
			System.out.println();
		}
	}
}
