package com.qubacy.interlocutor.ui.screen.play.results.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;

public class PlayResultsUserContactViewHolder extends RecyclerView.ViewHolder {
    private final TextView m_usernameTextView;
    private final TextView m_contactTextView;

    public PlayResultsUserContactViewHolder(@NonNull final View itemView) {
        super(itemView);

        m_usernameTextView = itemView.findViewById(R.id.play_results_user_contact_username);
        m_contactTextView = itemView.findViewById(R.id.play_results_user_contact_contact);
    }

    public boolean setData(
            final String username,
            final String contact)
    {
        if (username == null || contact == null) return false;

        m_usernameTextView.setText(username);
        m_contactTextView.setText(contact);

        return true;
    }
}
