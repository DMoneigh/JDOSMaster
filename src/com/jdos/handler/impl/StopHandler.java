package com.jdos.handler.impl;

import java.io.IOException;
import java.util.Map;

import com.jdos.handler.RequestHandler;
import com.jdos.server.Server;
import com.jdos.server.ServerManager;
import com.sun.net.httpserver.HttpExchange;

/**
 * The "/stop" handler.
 * 
 * @author Desmond Jackson
 */
public class StopHandler extends RequestHandler {
	
	/**
	 * Creates the "/stop" handler.
	 */
	public StopHandler() {
		super();
	}

	@Override
	public void execute(HttpExchange exchange, Map<String, String> parameters) throws IOException {
		for (Server server : ServerManager.getUsedServers())
			if (server.getUserId() == new Integer(parameters.get("id")))
				if (server.stopAttack(parameters.get("id"))) {
					writeResponse(exchange, "Successfully stopped attack on Server" + server.getId());
					return;
				}
		writeResponse(exchange, "Could not stop attack, try again.");
	}

	@Override
	public String[] getRequiredParameters() {
		return new String[] {"id"};
	}

}
