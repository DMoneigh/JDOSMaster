package com.jdos.handler.impl;

import java.io.IOException;
import java.util.Map;

import com.jdos.handler.RequestHandler;
import com.jdos.server.Server;
import com.jdos.server.ServerManager;
import com.sun.net.httpserver.HttpExchange;

/**
 * The "/attack" handler.
 * 
 * Required parameters: id, ip, port, method, and time.
 * 
 * @author Desmond Jackson
 */
public class AttackHandler extends RequestHandler {
	
	/**
	 * Creates the "/attack" handler.
	 */
	public AttackHandler() {
		super();
	}

	@Override
	public void execute(HttpExchange exchange, Map<String, String> parameters) throws IOException {
		for (Server server : ServerManager.getUnusedServers())
			if (server.isAlive() && !server.isAttacking())
				if (server.startAttack(parameters.get("id"), parameters.get("ip"), parameters.get("port"),
						parameters.get("method"), parameters.get("time"))) {
					writeResponse(exchange, "Attack sent on server " + server.getId());
					return;
				}
		writeResponse(exchange, "All servers are busy.");
	}

	@Override
	public String[] getRequiredParameters() {
		return new String[] {"id", "ip", "port", "method", "time"};
	}

}
