package com.example.andreas.androidchatclient.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andreas.androidchatclient.R;
import com.example.andreas.androidchatclient.dto.Message;

import java.util.List;

/**
 * Created by andreas on 2017-12-13.
 */

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewAdapter.ViewHolder> {

    private List<Message> messages;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView, messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            messageTextView = itemView.findViewById(R.id.message_text_view);
        }

    }

    public ChatViewAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.nameTextView.setText(message.getUsername());
        holder.messageTextView.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
