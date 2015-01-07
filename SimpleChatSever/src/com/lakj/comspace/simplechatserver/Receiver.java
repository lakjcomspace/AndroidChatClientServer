package com.lakj.comspace.simplechatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

/**
 * This is the chat receiver. This works as a thread and continuously check
 * whether a message is available in the input buffer.
 * 
 * @author Lak J Comspace (http://lakjeewa.blogspot.com)
 * 
 */
public class Receiver implements Runnable {

    private BufferedReader bufferedReader;
    private JTextArea chatView;

    /**
     * Public constructor.
     * 
     * @param clientSocket
     *            Socket which has been opened for chat
     * @param chatViewParam
     *            Chat history text area of chat window
     */
    public Receiver(Socket clientSocket, JTextArea chatViewParam) {
        chatView = chatViewParam;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        String message;

        while (true) {
            try {
                if (bufferedReader.ready()) {
                    message = bufferedReader.readLine(); // Read the chat message.
                    chatView.append("Client: " + message + "\n"); // Print the chat message on chat window.
                }
            } catch (IOException ex) {
                System.out.println("Problem in message reading");
                ex.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }

    }

}
