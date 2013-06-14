package com.ircclient;

import java.io.*;
import java.net.*;

public class IRCClient {

    public static void main(String[] args) throws Exception {

        // The server to connect to and our details.
        String server = "irc.renaporn.com";
        String nick = "DarkBot";
        String login = "DarkBot";
        int port = 6667;

        // The channel which the bot will join.
        String channel = "#letscode";
        
        String stockMsg = "Hi!";
        
        int timer = 1;
        boolean flag = false;
        
        // Connect directly to the IRC server.
        Socket socket = new Socket(server, port);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));

        
        // Log on to the server.
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + login + " 0 0 : DarkBot by Alex\r\n");
        writer.flush();
        
        // Read lines from the server until it tells us we have connected.
        String line = null;
        String prevLine = null;
        while ((line = reader.readLine( )) != null) {
        	System.out.println(line);
            if (line.indexOf("004") >= 0) {
            	
                // We are now logged in.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
            //else System.out.println("b");
        }
        
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        System.out.println("joining");
        writer.flush();
        timer = 1;
        
        // Keep reading lines from the server.
        while ((line = reader.readLine( )) != null) {
            if (line.startsWith("PING ")) {
                // We must respond to PINGs to avoid being disconnected.
                writer.write("PONG " + line.substring(5) + "\r\n");
                System.out.println("pinged");
                //writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush( );
            }
            else if(timer % 2 == 0 && flag == true) {
            	writer.write("PRIVMSG " + channel + " :" + stockMsg + "\r\n");
            	writer.flush();
            	System.out.println(line);
            	System.out.println("<DarkBot> " + stockMsg);
            	timer = 1;
            }
            else if(line != null){
                // Print the raw line received by the bot.
            	//String[] nameParse = line.split("!");
            	//String[] messageParse = nameParse[1].split(":");
            	if (line.indexOf("366") >= 0) {
            		flag = true;
            		timer = 1;
            	}
            	else if(line.indexOf("gtfo") >= 0) {
            		System.out.println(timer + " " + line);
            		return;
            	}
            	else timer++;
                System.out.println(timer + " " + line);
                prevLine = line;
            }
        }
    }

}	