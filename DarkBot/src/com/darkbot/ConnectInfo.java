package com.darkbot;

public class ConnectInfo {

	public String owner = "Alex";
	
	public String nick = "";			// Name as you appear to others
	public String username = "";		// Part of your host--your name as it appears to the server, I guess
	public String realName = "";		// For now, creator's stamp
	public String password = "";		// I'll encrypt it later or something
	public String quitMessage = "";
	
	public String server = "";			// These will be relocated in the future
	public int port = 6667;				// 6667 is the default port for a lot of servers
	
	public String channel = "";
	
	public ConnectInfo() {
		this.nick = "DarkBot";
		this.username = "DarkBot";
		this.realName = "DarkBot by TheGreekBrit";
		this.password = null;
		this.quitMessage = "ok bye";
		this.server = "irc.renaporn.com";
		this.port = 6667;								// Redeclaring it for consistency's sake
		this.channel = "#letscode";						// Note to self: this should be a list (or array) of some kind in the future (maybe?)
	}
	
}
