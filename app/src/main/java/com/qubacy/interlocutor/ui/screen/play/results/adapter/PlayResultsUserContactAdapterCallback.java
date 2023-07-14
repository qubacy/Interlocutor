package com.qubacy.interlocutor.ui.screen.play.results.adapter;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public interface PlayResultsUserContactAdapterCallback {
    public MatchedUserProfileData getUserProfileDataByIndex(final int index);
    public ProfilePublic getProfileById(final int profileId);
    public int getUserProfileContactDataCount();
    public void copyContactToClipboard(final String contact);
}
