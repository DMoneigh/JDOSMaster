package com.jdos.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Represents a request handler.
 * 
 * @author Desmond Jackson
 */
public abstract class RequestHandler extends Object implements HttpHandler {

	/**
	 * Creates the request handler.
	 */
	public RequestHandler() {
		super();
	}

	/**
	 * Writes a response to the request.
	 * 
	 * @param exchange The HTTP exchange
	 * 
	 * @param response The response to write
	 * 
	 * @throws IOException 
	 */
	public void writeResponse(HttpExchange exchange, String response) throws IOException {
		exchange.sendResponseHeaders(200, response.length());
		OutputStream out = exchange.getResponseBody();
		out.write(response.getBytes());
		out.close();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String query = exchange.getRequestURI().getQuery();
		if (query == null) {
			writeResponse(exchange, "Must query this page!");
			return;
		}
		if (!query.contains("&") && getRequiredParameters().length > 1) {
			writeResponse(exchange, "Specify more parameters!");
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		for (String parameter : query.split("&")) {
			if (!parameter.contains("=") || parameter.substring(parameter.indexOf("=")).isEmpty()) {
				writeResponse(exchange, "Set the value of the required parameter!");
				return;
			}
			String[] pair = parameter.split("=");
			parameters.put(pair[0], pair[1]);
		}
		if (getRequiredParameters() != null)
			for (String parameter : getRequiredParameters()) {
				if (parameters.keySet().contains(parameter))
					continue;
				else {
					writeResponse(exchange, "Specify all parameters!");
					return;
				}
			}
		execute(exchange, parameters);
		exchange.close();
	}

	/**
	 * Called if request passes the parameter check.
	 * 
	 * @param exchange The HTTP exchange
	 * 
	 * @param parameters The parameters to use in the execution
	 * 
	 * @throws IOException 
	 */
	public abstract void execute(HttpExchange exchange, Map<String, String> parameters) throws IOException;

	/**
	 * Gets the required parameters.
	 * 
	 * @return The required parameters
	 */
	public abstract String[] getRequiredParameters();

}
