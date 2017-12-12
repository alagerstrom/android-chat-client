package com.example.andreas.androidchatclient.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andreas.androidchatclient.R;
import com.example.andreas.androidchatclient.controller.Controller;
import com.example.andreas.androidchatclient.net.ChatClient;

public class MainActivity extends AppCompatActivity implements ChatClient.Delegate {

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
    }

    public void sendMessage() {
        EditText editText = findViewById(R.id.send_message_edit_text);
        String message = editText.getText().toString();
        editText.setText("");
        Controller.getInstance().send(message);
    }

    @Override
    public void incomingMessage(final String username, final String message) {
        final TextView textView = findViewById(R.id.chat_display_text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(username + " says: " + message + "\n");
            }
        });
    }

    public void sendMessageButtonClicked(View view) {
        sendMessage();
    }
}
