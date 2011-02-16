package net.geant.autobahn.framework;

import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class MyTelnetClient {

	public static final String PASSWORD_PROMPT = "Give password:";
	
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;

	public MyTelnetClient(String server, int port, String pass) {
		try {
			// Connect to the specified server
			telnet.connect(server, port);

			// Get input and output stream references
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			// Advance to a prompt
			readUntil(PASSWORD_PROMPT);
			write(pass);
			
			readUntil(Framework.PROMPT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			boolean found = false;
			char ch = (char) in.read();
			while (true) {
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void write(String value) throws Exception {
		out.println(value);
		out.flush();
	}

	public void disconnect() throws Exception {
		telnet.disconnect();
	}

}
