package net.geant.autobahn.testplatform.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	    	System.out.println("Error when reading the file. " + e.getMessage());
	    }
	}
	
	public MyProperties(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}
	
	public void setProperty(String name, String val) {
		for(int i = 0; i < content.size(); i++) {
			if(content.get(i).startsWith(name + "=")) {
				content.set(i, name + "=" + val);
				return;
			}
		}
		
		content.add(name + "=" + val);
	}
	
	public void replaceLineContaining(String part, String newLine) {
		for(int i = 0; i < content.size(); i++) {
			if(content.get(i).contains(part)) {
				content.set(i, newLine);
			}
		}
	}
	
	public String getProperty(String name) {
		for(String line : content) {
			if(line.startsWith(name + "="))
				return line.replace(name + "=", "");
		}
		
		return null;
	}
	
	public void save(File file) {
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(file));
	        for(String line : content) {
		        out.write(line);
		        out.write("\n");
	        }
	        out.close();
	    } catch (IOException e) {
	    }
	}
	
	public static Properties loadPropertiesFile(File file) {
		Properties properties = new Properties();
	    try {
	        properties.load(new FileInputStream(file));
	    } catch (Exception e) {
	    	System.out.println("Failed to load properties: " + 
					file.getName() + ", " + e.getMessage());
	    }
	    
	    return properties;
	}
	
	public static void savePropertiesFile(Properties properties, File file) {
		// Write properties file.
	    try {
	    	OutputStream fos = new FileOutputStream(file);
	        properties.store(fos, null);
	        fos.close();
	    } catch (IOException e) {
			System.out.println("Failed to save properties: " + 
					file.getName() + ", " + e.getMessage());
	    }
	}
}
