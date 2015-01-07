package com.lakj.comspace.simplechatserver;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This is the GUI component for chat window.
 * 
 * @author Lak J Comspace (http://lakjeewa.blogspot.com)
 * 
 */
public class ChatWindow extends JFrame {

    private JTextArea chatView;
    private JButton sendButton;
    private JTextArea chatBox;

    /**
     * This method open the chat window.
     * 
     * @param clientSocket
     *            Socket which has been opened for chat
     */
    public void open(final Socket clientSocket) {

        initComponents();

        setWinodwCloseListnerToCloseSocket(clientSocket);

        initSenderAndReceiver(clientSocket);

    }

    /**
     * This method initialize the components of the chat winodow.
     */
    private void initComponents() {

        chatView = new JTextArea(20, 46);
        JScrollPane chatViewScrollPane = new JScrollPane(chatView);
        chatBox = new JTextArea(5, 40);
        JScrollPane chatBoxScrollPane = new JScrollPane(chatBox);
        sendButton = new JButton("Send");

        setResizable(false);
        setTitle("Chat Server");
        setSize(550, 450);
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(chatViewScrollPane);
        contentPane.add(chatBoxScrollPane);
        contentPane.add(sendButton);
        chatView.setEditable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This method initialize the chat sender and receiver for this chat window.
     * 
     * @param clientSocket
     *            Socket which has been opened for chat
     */
    private void initSenderAndReceiver(final Socket clientSocket) {

        Receiver receiver = new Receiver(clientSocket, chatView);
        final Sender sender = new Sender(clientSocket, chatView);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sender.sendMessage(chatBox.getText());
                chatBox.setText(""); //Clear the chat box
            }
        });

        Thread receiverThread = new Thread(receiver);
        receiverThread.run();
    }

    /**
     * This method register window closing listener to close the clientSocket
     * when the chat window is closed.
     * 
     * @param clientSocket
     *            Socket which has been opened for chat
     */
    private void setWinodwCloseListnerToCloseSocket(final Socket clientSocket) {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
