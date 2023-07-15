package com.qubacy.interlocutor.ui.screen.play.choosing.adapter;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import org.jetbrains.annotations.NotNull;

public interface PlayChoosingUserAdapterCallback {
    public int getUserCount();
    public ProfilePublic getProfileByIndex(final int index);
    public boolean isUserChosenById(final int userId);
    public boolean addChosenUserId(final int userId);
    public boolean removeChosenUserId(final int userId);
    public boolean isChoosingEnabled();
    public void onUserAdapterErrorOccurred(@NotNull final Error error);
}
