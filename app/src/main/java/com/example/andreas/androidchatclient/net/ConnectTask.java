package com.example.andreas.androidchatclient.net;

import android.os.AsyncTask;

import com.example.andreas.androidchatclient.controller.CompletionHandler;

public class ConnectTask extends AsyncTask<Void, Void, Void> {

    private final String username;
    private final String host;
    private final int port;
    private final CompletionHandler<ChatClient> completionHandler;


    public ConnectTask(String username, String host, int port, CompletionHandler<ChatClient> completionHandler) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.completionHandler = completionHandler;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ChatClient chatClient = new ChatClient(username, host, port);
            completionHandler.onSuccess(chatClient);
        } catch (Exception e) {
            completionHandler.onFailure(e);
        }
        return null;
    }

}
