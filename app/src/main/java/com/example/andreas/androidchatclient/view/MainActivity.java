package com.example.andreas.androidchatclient.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andreas.androidchatclient.R;
import com.example.andreas.androidchatclient.controller.Controller;
import com.example.andreas.androidchatclient.net.ChatClient;
import com.example.andreas.androidchatclient.dto.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatClient.Delegate {


    private List<Message> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatViewAdapter chatViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller.getInstance().setChatClientDelegate(this);

        EditText editText = findViewById(R.id.send_message_edit_text);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        chatViewAdapter = new ChatViewAdapter(messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Controller.getInstance().isConnected()){
            Intent intent = new Intent(this, ConnectActivity.class);
            startActivity(intent);
        }
    }

    public void sendMessage() {
        EditText editText = findViewById(R.id.send_message_edit_text);
        String message = editText.getText().toString();
        editText.setText("");
        Controller.getInstance().send(message);
    }

    @Override
    public void incomingMessage(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.add(message);
                chatViewAdapter.notifyItemInserted(messages.size() - 1);
                recyclerView.scrollToPosition(messages.size() - 1);
            }
        });
    }

    public void sendMessageButtonClicked(View view) {
        sendMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Controller.getInstance().disconnect();
    }
}
