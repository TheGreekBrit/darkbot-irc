package com.darkbot;

import java.io.*;
import java.net.*;

public class ServerEvent extends ConnectInfo {
	
	private Socket socket;
	public BufferedWriter writer;
	public BufferedReader reader;
	
	private String line = null;
	private String nickname = null;
	
	public ServerEvent(String server, int port) throws IOException {
		
		this.socket = new Socket(server, port);
		this.writer = new BufferedWriter(
				new OutputStreamWriter(this.socket.getOutputStream()));
		this.reader = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream()));
		
		logIn();
		
		if(consoleTab())
			channelTab();
	}
	
	
	private void logIn() throws IOException {
		
		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + username + " 0 0 : " + realName + "\r\n");
		writer.flush();
		
	}
	
	
	// For displaying the stuff the server throws at you before joining (MOTD, etc)
	private boolean consoleTab() throws IOException {

		while((this.line = reader.readLine()) != null) {
			printFormattedLine(line);
			
			checkPing(line);
			
			if(line.indexOf("376") >= 0)
				// We are now logged in
				break;
			
			else if(line.indexOf("433") >= 0) {
				System.out.println("Nickname is already in use.\nQuitting...");		// TODO: Handle alt nicks
				return false;
			}
		}
		
		joinChannel(this.channel);
		return true;
		
	}
	
	private void channelTab() throws IOException {
		while((line = reader.readLine( )) != null) {
			checkPing(line);
			
			printFormattedLine(line);
			
			if(line.indexOf("gtfo") >= 0) {
				quit();
				//return;
			}
		}
	}
	
	private void printFormattedLine(String raw) {
		
		if(raw.charAt(0) == ':')
			raw = raw.substring(1);
		
		int endch = raw.indexOf('!');
		
		if(endch == -1)
			nickname = "Server";
		else
			nickname = raw.substring(0, endch);
		
		String[] parsed = null;
		
		if(raw.contains(":")) {
			parsed = raw.split(":", 2);
			System.out.printf("<%s> %s\n", nickname, parsed[1]);
		}
		
	}
	
	private void joinChannel(String chan) throws IOException {
		writer.write("JOIN " + chan + "\r\n");
		writer.flush();
		return;
	}
	
	
	private void checkPing(String raw) throws IOException {
		if(raw.startsWith("PING ")) {
			writer.write("PONG " + raw.substring(5) + "\r\n");
			writer.flush();
		}
		return;
	}
	
	
	private void quit() throws IOException {
		writer.write("QUIT " + quitMessage + "\r\n");
		writer.flush();
		return;
	}
	
	
	
}
