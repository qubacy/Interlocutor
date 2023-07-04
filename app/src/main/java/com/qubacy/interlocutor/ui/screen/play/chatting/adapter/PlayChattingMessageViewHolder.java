package com.qubacy.interlocutor.ui.screen.play.chatting.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public class PlayChattingMessageViewHolder extends RecyclerView.ViewHolder {
    private final TextView m_usernameTextView;
    private final TextView m_textView;

    public PlayChattingMessageViewHolder(@NonNull final View itemView) {
        super(itemView);

        m_usernameTextView = itemView.findViewById(R.id.play_chatting_message_username);
        m_textView = itemView.findViewById(R.id.play_chatting_message_text);
    }

    public boolean setData(
            @NonNull final ProfilePublic sender,
            @NonNull final Message message)
    {
        m_usernameTextView.setText(sender.getUsername());
        m_textView.setText(message.getText());

        return true;
    }
}
