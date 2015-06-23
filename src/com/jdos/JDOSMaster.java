package com.jdos;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.jdos.handler.impl.AttackHandler;
import com.jdos.handler.impl.CheckHandler;
import com.jdos.handler.impl.StopHandler;
import com.jdos.server.Server;
import com.jdos.util.FileManager;
import com.sun.net.httpserver.HttpServer;

/**
 * A Java Denial of Service server master.
 * 
 * @author Desmond Jackson
 */
public class JDOSMaster extends Object {
	
	/**
	 * The port that the JDOSMaster runs on.
	 */
	private static final int LOCAL_PORT = 4545;
	
	/**
	 * An array of attack servers.
	 */
	public static final Server[] ATTACK_SERVERS = new Server[] {
		new Server(1, "127.0.0.1", 4546),
	};
	
	/**
	 * The main method.
	 * 
	 * @param args String arguments 
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileManager.createLogFile();
		HttpServer webServer = HttpServer.create(new InetSocketAddress(LOCAL_PORT), 0);
		webServer.createContext("/attack", new AttackHandler());
		webServer.createContext("/check", new CheckHandler());
		webServer.createContext("/stop", new StopHandler());
		webServer.start();
	}

}
