package net.geant.autobahn.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyProperties {

	private List<String> content = null;

	public MyProperties() {
		content = new ArrayList<String>();
	}

	public MyProperties(InputStream is) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String str;
			List<String> lines = new ArrayList<String>();
			while ((str = in.readLine()) != null) {
				lines.add(str);
			}
			in.close();
			is.close();

			content = lines;
		} catch (IOException e) {
			System.out
					.println("Error when reading the file. " + e.getMessage());
		}
	}

	public MyProperties(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public void setProperty(String name, String val) {
		for (int i = 0; i < content.size(); i++) {
			if (content.get(i).startsWith(name + "=")) {
				content.set(i, name + "=" + val);
				return;
			}
		}

		content.add(name + "=" + val);
	}

	public void replaceLineContaining(String part, String newLine) {
		for (int i = 0; i < content.size(); i++) {
			if (content.get(i).contains(part)) {
				content.set(i, newLine);
			}
		}
	}

	public String getProperty(String name) {
		for (String line : content) {
			if (line.startsWith(name + "="))
				return line.replace(name + "=", "");
		}

		return null;
	}

	public void save(File file) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (String line : content) {
				out.write(line);
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
		}
	}
}
