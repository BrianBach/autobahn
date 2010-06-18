/**
 * 
 */
package net.geant.autobahn.tool.pionier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * @author Michal
 *
 */

public class FoundryOutputParser {
	
	InputStream sshInputStream;
	
	public FoundryOutputParser(InputStream sshInputStream) {
		this.sshInputStream = sshInputStream;
	}
	
	
	public void GetId() {

		BufferedReader br = new BufferedReader(new InputStreamReader(sshInputStream));

		while (true)
		{
		
			String line;
			try {
				line = br.readLine();
				if (line == null) {
					System.out.println("Pusta linia");
					break;}
				System.out.println(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
