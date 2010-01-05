/*
 * Copyright 2005-2008 RedIRIS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.geant.edugain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class AESTool {

	private Map<String, byte[]> keys;
	private BouncyCastleProvider bcp;

	/**
	 * Generate an <tt>AESTool</tt> object
	 * 
	 */
	public AESTool() {
		keys = new HashMap<String, byte[]>();
		bcp = new BouncyCastleProvider();
	        java.security.Security.addProvider((Provider)bcp);

	}

	/**
	 * Add a valid key setting its name.
	 * 
	 * @param name
	 *                The name of the key
	 * @param path
	 *                Path to file
	 * @return <tt>true</tt> if the key is valid and it wasn't previously
	 *         added, <tt>false</tt> in other case
	 * @throws IOException 
	 */
	public boolean addKey(String name, String path) throws IOException {
	            BufferedReader aesFile = new BufferedReader(new FileReader(path));
	            byte[] keyBytes = aesFile.readLine().getBytes();
	            aesFile.close();
	            return this.addKey(name, keyBytes);

	}
	
	
	/**
	 * Add a valid key setting its name.
	 * 
	 * @param name
	 *                The name of the key
	 * @param key
	 *                The array of bytes defining the key
	 * @return <tt>true</tt> if the key is valid and it wasn't previously
	 *         added, <tt>false</tt> in other case
	 */
	public boolean addKey(String name, byte[] key) {
		if (key == null || name == null || keys.containsKey(name))
			return false;
		// AJAY: Using && instead of ||
		if (key.length != 16 && key.length != 24 && key.length != 32) {
			return false;
		}
		keys.put(name, key);
		return true;
	}

	/**
	 * Delete a previously added key
	 * 
	 * @param name
	 *                The name of the key
	 * @return <tt>true</tt> if it has removed, <tt>false</tt> in other
	 *         case
	 */
	public boolean delKey(String name) {
		if (name == null || !keys.containsKey(name))
			return false;
		keys.remove(name);
		return true;
	}

	/**
	 * Delete all added keys
	 * 
	 */
	public void clearKeys() {
		keys.clear();
	}

	/**
	 * Get an array with the name of all added keys
	 * 
	 * @return An array with their names
	 */
	public String[] getNameKeys() {
		String[] res = new String[keys.size()];
		res = keys.keySet().toArray(res);
		return res;
	}

	/**
	 * Encrypt a message using the specified key.
	 * 
	 * @param nameKey
	 *                The name of the key you want to use
	 * @param data
	 *                The message you want to encrypt
	 * @return The ciphered message in base 64, or <tt>null</tt> if it has
	 *         happened any error encrypting
	 */
	public String encode(String nameKey, String data) {
		try {
			Cipher cipher = Cipher
					.getInstance("AES/ECB/PKCS5Padding");
			// setup key
			byte[] keyBytes = (byte[]) keys.get(nameKey);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes,
					"AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] rawData = data.getBytes();
			byte[] results = cipher.doFinal(rawData);
			// return new String(base64Encoder.encode(results));
			String output = new String(Base64.encode(results));
			return output;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Decrypt a message using the specified key
	 * 
	 * @param nameKey
	 *                The name of the key you want to use
	 * @param data
	 *                The ciphered message in base 64
	 * @return The "plain" message, or <tt>null</tt> if it has happened
	 *         any error
	 */
	public String decode(String nameKey, String data) {
		try {
			Cipher cipher = Cipher
					.getInstance("AES/ECB/PKCS5Padding");
			// setup key
			byte[] keyBytes = (byte[]) keys.get(nameKey);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes,
					"AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			// byte[] rawData = base64Decoder.decodeBuffer(data);
			byte[] rawData = Base64.decode(data.getBytes());
			byte[] results = cipher.doFinal(rawData);
			return new String(results);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean hasKey(String nameKey) {
		return this.keys.containsKey(nameKey);
	}
}
