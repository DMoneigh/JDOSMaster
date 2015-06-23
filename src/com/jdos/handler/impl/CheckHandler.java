package com.jdos.handler.impl;

import java.io.IOException;
import java.util.Map;

import com.jdos.JDOSMaster;
import com.jdos.handler.RequestHandler;
import com.jdos.server.Server;
import com.sun.net.httpserver.HttpExchange;

/**
 * The "/check" handler.
 * 
 * @author Desmond Jackson
 */
public class CheckHandler extends RequestHandler {
	
	/**
	 * Creates the "/check" handler.
	 */
	public CheckHandler() {
		super();
	}

	@Override
	public void execute(HttpExchange exchange, Map<String, String> parameters) throws IOException {
		String string = "";
		for (Server server : JDOSMaster.ATTACK_SERVERS)
			string = string + "Server " + server.getId() + ": " + server.getRemainingAttackTime() + "\n"; 
		writeResponse(exchange, string);
	}

	@Override
	public String[] getRequiredParameters() {
		return null;
	}

}
