package com.example.andreas.androidchatclient.controller;


import com.example.andreas.androidchatclient.net.ChatClient;
import com.example.andreas.androidchatclient.net.ConnectTask;
import com.example.andreas.androidchatclient.util.Constants;

/**
 * Created by andreas on 2017-12-12.
 */

public class Controller {

    private static Controller instance = new Controller();
    private ChatClient chatClient;

    private Controller(){
    }

    public boolean isConnected(){
        return chatClient != null;
    }

    public static Controller getInstance(){
        return instance;
    }

    public void connect(String username, String host, final CompletionHandler<Void> completionHandler) {
        ConnectTask connectTask = new ConnectTask(username, host, Constants.PORT, new CompletionHandler<ChatClient>() {
            @Override
            public void onSuccess(ChatClient chatClient) {
                Controller.this.chatClient = chatClient;
                completionHandler.onSuccess(null);
            }

            @Override
            public void onFailure(Exception exception) {
                completionHandler.onFailure(exception);
            }
        });
        connectTask.execute();
    }

    public void send(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                chatClient.send(message);
            }
        }).start();
    }

    public void setChatClientDelegate(ChatClient.Delegate delegate) {
        chatClient.setDelegate(delegate);
    }

    public void disconnect() {
        if (chatClient != null) {
            chatClient.disconnect();
        }
        chatClient = null;
    }
}
