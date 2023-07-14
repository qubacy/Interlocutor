package com.qubacy.interlocutor.ui.screen.play.results.model;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public interface PlayResultsViewModel {
    public ProfilePublic getProfileById(final int profileId);
    public MatchedUserProfileData getMatchedUserProfileDataByIndex(final int index);
    public int getMatchedUserProfileDataCount();
}
