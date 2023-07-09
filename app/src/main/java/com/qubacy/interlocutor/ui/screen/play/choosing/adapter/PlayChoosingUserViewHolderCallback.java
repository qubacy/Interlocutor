package com.qubacy.interlocutor.ui.screen.play.choosing.adapter;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public interface PlayChoosingUserViewHolderCallback {
    public void onUserChoosingStateChange(
            @NonNull final ProfilePublic profilePublic,
            final boolean isChosen);
}
