package com.qubacy.interlocutor.ui.screen.play.results.adapter;

import androidx.annotation.Nullable;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import org.jetbrains.annotations.NotNull;

public interface PlayResultsUserContactAdapterCallback {
    public MatchedUserProfileData getUserProfileDataByIndex(final int index);
    public ProfilePublic getProfileById(final int profileId);
    public int getUserProfileContactDataCount();
    public void copyContactToClipboard(@Nullable final String contact);
    public void onUserContactAdapterErrorOccurred(@NotNull final Error error);
}
