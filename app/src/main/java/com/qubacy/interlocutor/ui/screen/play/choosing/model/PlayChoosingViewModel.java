package com.qubacy.interlocutor.ui.screen.play.choosing.model;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import java.util.List;

public interface PlayChoosingViewModel {
    public long getChoosingDuration();
    public List<ProfilePublic> getUserList();
    public ProfilePublic getProfileByIndex(final int index);
    public int getUserCount();
}
