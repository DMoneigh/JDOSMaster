package com.jdos.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jdos.JDOSMaster;

/**
 * The server manager.
 * 
 * @author Desmond Jackson
 */
public class ServerManager extends Object {
	
	/**
	 * A map of attacking servers.
	 */
	private static final Map<String, Server> ATTACKING_SERVERS = new HashMap<String, Server>();
	
	/**
	 * Updates the attacking servers map.
	 */
	private static void updateAttackingServers() {
		for (Entry<String, Server> entry : ATTACKING_SERVERS.entrySet())
			if (!entry.getValue().isAttacking())
				ATTACKING_SERVERS.remove(entry.getKey());
	}
	
	/**
	 * Appends an attacking server to the map.
	 * 
	 * @param userId The id of the user who started the attack
	 * 
	 * @param server The server
	 */
	public static void addAttackingServer(String userId, Server server) {
		updateAttackingServers();
		ATTACKING_SERVERS.put(userId, server);
	}
	
	/**
	 * Removes an attacking server from the map.
	 * 
	 * @param userId The id of the user who started the attack
	 */
	public static void removeAttackingServer(String userId) {
		updateAttackingServers();
		ATTACKING_SERVERS.get(userId).resetTimes();
		ATTACKING_SERVERS.remove(userId);
	}
	
	/**
	 * Gets whether a user can boot or not.
	 * 
	 * @param userId The user id of the user
	 * 
	 * @return false if the user cannot boot
	 */
	public static boolean canBoot(String userId) {
		updateAttackingServers();
		return !ATTACKING_SERVERS.keySet().contains(userId);
	}
	
	/**
	 * Gets an array of unused servers.
	 * 
	 * @return An array of unused servers
	 */
	public static synchronized Server[] getUnusedServers() {
		List<Server> unusedServers = new ArrayList<Server>();
		for (Server server : JDOSMaster.ATTACK_SERVERS)
			if (!server.isAttacking())
				unusedServers.add(server);
		return unusedServers.toArray(new Server[unusedServers.size()]);
	}
	
	public static synchronized Server[] getUsedServers() {
		List<Server> usedServers = new ArrayList<Server>();
		for (Server server : JDOSMaster.ATTACK_SERVERS)
			if (server.isAttacking())
				usedServers.add(server);
		return usedServers.toArray(new Server[usedServers.size()]);
	}
	
}
