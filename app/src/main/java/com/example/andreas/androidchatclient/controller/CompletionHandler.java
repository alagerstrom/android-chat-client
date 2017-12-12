package com.example.andreas.androidchatclient.controller;

public interface CompletionHandler<T>{
    void onSuccess(T connection);
    void onFailure(Exception exception);
}
