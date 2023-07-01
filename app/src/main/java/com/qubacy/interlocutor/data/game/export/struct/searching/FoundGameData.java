package com.qubacy.interlocutor.data.game.export.struct.searching;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import java.io.Serializable;
import java.util.List;

public class FoundGameData implements Serializable {
    final private List<ProfilePublic> m_profilePublicList;

    protected FoundGameData(
            final List<ProfilePublic> profilePublicList)
    {
        m_profilePublicList = profilePublicList;
    }

    public static FoundGameData getInstance(
            final List<ProfilePublic> profilePublicList)
    {
        if (profilePublicList == null) return null;
        if (profilePublicList.isEmpty()) return null;

        return new FoundGameData(profilePublicList);
    }

    public List<ProfilePublic> getProfilePublicList() {
        return m_profilePublicList;
    }
}
