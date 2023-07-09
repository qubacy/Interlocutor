package com.qubacy.interlocutor.ui.screen.play.choosing.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public class PlayChoosingUserViewHolder extends RecyclerView.ViewHolder {
    private final PlayChoosingUserViewHolderCallback m_callback;

    private final CheckBox m_isChosenCheckBox;
    private final TextView m_usernameTextView;

    public PlayChoosingUserViewHolder(
            @NonNull final View itemView,
            @NonNull final PlayChoosingUserViewHolderCallback callback)
    {
        super(itemView);

        m_callback = callback;

        m_isChosenCheckBox =
                itemView.findViewById(R.id.play_choosing_user_button_choose);
        m_usernameTextView =
                itemView.findViewById(R.id.play_choosing_user_username);
    }

    public boolean setData(
            final ProfilePublic profile,
            final boolean isChosen,
            final boolean isChoosingEnabled)
    {
        if (profile == null) return false;

        m_isChosenCheckBox.setChecked(isChosen);
        m_isChosenCheckBox.setEnabled(isChoosingEnabled);
        m_usernameTextView.setText(profile.getUsername());

        m_isChosenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(
                    final CompoundButton buttonView,
                    final boolean isChecked)
            {
                m_callback.onUserChoosingStateChange(profile, isChecked);
            }
        });

        return true;
    }
}
