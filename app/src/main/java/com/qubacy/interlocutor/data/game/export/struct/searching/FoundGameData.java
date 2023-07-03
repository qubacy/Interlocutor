package com.qubacy.interlocutor.data.game.export.struct.searching;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import java.io.Serializable;
import java.util.List;

public class FoundGameData extends FoundGameDataBase implements Serializable {
    protected final List<ProfilePublic> m_profilePublicList;

    protected FoundGameData(
            final int localProfileId,
            final List<ProfilePublic> profilePublicList)
    {
        super(localProfileId);

        m_profilePublicList = profilePublicList;
    }

    public static FoundGameData getInstance(
            final int localProfileId,
            final List<ProfilePublic> profilePublicList)
    {
        if (profilePublicList == null || localProfileId < 0)
            return null;
        if (profilePublicList.isEmpty()) return null;

        return new FoundGameData(localProfileId, profilePublicList);
    }

    public List<ProfilePublic> getProfilePublicList() {
        return m_profilePublicList;
    }
}
