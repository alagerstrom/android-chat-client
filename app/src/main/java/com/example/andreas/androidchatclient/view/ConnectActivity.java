package com.example.andreas.androidchatclient.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreas.androidchatclient.R;
import com.example.andreas.androidchatclient.controller.CompletionHandler;
import com.example.andreas.androidchatclient.controller.Controller;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        EditText usernameEditText = findViewById(R.id.username_edit_text);
        usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    connect();
                    return true;
                }
                return false;
            }
        });
    }

    public void connectButtonClicked(View view){
        connect();
    }
    public void connect() {
        EditText hostEditText = findViewById(R.id.host_edit_text);
        EditText usernameEditText = findViewById(R.id.username_edit_text);

        String host = hostEditText.getText().toString();
        String username = usernameEditText.getText().toString();

            Controller.getInstance().connect(username, host, new CompletionHandler<Void>() {
                @Override
                public void onSuccess(Void connection) {
                    Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Exception exception) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ConnectActivity.this, "Failed to connect", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });


    }
}
