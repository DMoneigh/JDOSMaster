package com.jdos.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * The file manager.
 * 
 * @author Desmond Jackson
 */
public class FileManager extends Object {
	
	/**
	 * The "log.txt" file.
	 */
	private static final File LOG = new File("log.txt");
	
	/**
	 * Creates the "log.txt" file.
	 * 
	 * @throws IOException
	 */
	public static void createLogFile() throws IOException {
		if (!LOG.exists())
			LOG.createNewFile();
	}
	
	/**
	 * Stores an attack into the log.
	 * 
	 * @param id The id of the attacker
	 * 
	 * @param ip The target ip
	 * 
	 * @param port The target port
	 * 
	 * @param method The method used
	 * 
	 * @param time The length in seconds of the attack
	 * 
	 * @throws IOException 
	 */
	public static synchronized void storeAttack(String id, String ip, String port, String method,
			String time) throws IOException {
		FileWriter fw = new FileWriter(LOG, true);
		fw.write(new Date(System.currentTimeMillis()) + " ");
		fw.write(id + " attacked " + ip + " on port " + port + " with method " + method + " for " + time + " ");
		fw.write("seconds.\n");
		fw.close();
	}
	
	/**
	 * Stores an attack stop.
	 * 
	 * @param id The id of the attacker
	 * 
	 * @throws IOException
	 */
	public static synchronized void storeStop(String id) throws IOException {
		FileWriter fw = new FileWriter(LOG, true);
		fw.write(new Date(System.currentTimeMillis()) + " ");
		fw.write(id + " stopped their attack!\n");
		fw.close();
	}

}
