package com.lakj.comspace.simplechatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the main class for chat server.
 * 
 * @author Lak J Comspace (http://lakjeewa.blogspot.com)
 * 
 */
public class SimpleChatServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server started. Listening to the port 4444. Waitng for the client.");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected on port 4444.");
        } catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
            e.printStackTrace();
            return;
        }

        ChatWindow chatWindow = new ChatWindow();
        chatWindow.open(clientSocket);
    }

}
