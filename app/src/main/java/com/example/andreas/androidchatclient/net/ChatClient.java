package com.example.andreas.androidchatclient.net;

/**
 * Created by andreas on 2017-12-12.
 */

import com.example.andreas.androidchatclient.dto.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatClient extends Thread {

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private Delegate delegate;
    private String username;
    private boolean connected = false;

    public void disconnect() {
        try {
            connected = false;
            socket.close();
        } catch (IOException ignored) {

        }
    }

    public interface Delegate {
        void incomingMessage(Message message);
    }

    public ChatClient(String username, String host, int port) throws IOException {
        this.username = username;
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        connected = true;
        start();
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public synchronized void send(String text) {
        Message message = new Message();
        message.setUsername(username);
        message.setText(text);
        writer.println(message.toString());
    }

    @Override
    public void run() {
        while (connected) {
            try {
                Message message = new Message(reader.readLine());
                if (delegate != null)
                    delegate.incomingMessage(message);
            } catch (IOException e) {
                connected = false;
            }
        }
        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }
}

