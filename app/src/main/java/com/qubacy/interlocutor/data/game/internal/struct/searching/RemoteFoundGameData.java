package com.qubacy.interlocutor.data.game.internal.struct.searching;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameDataBase;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.List;

public class RemoteFoundGameData extends FoundGameDataBase {
    final private List<RemoteProfilePublic> m_remoteProfilePublicList;

    protected RemoteFoundGameData(
            final int localProfileId,
            final List<RemoteProfilePublic> remoteProfilePublicList)
    {
        super(localProfileId);

        m_remoteProfilePublicList = remoteProfilePublicList;
    }

    public static RemoteFoundGameData getInstance(
            final int localProfileId,
            final List<RemoteProfilePublic> remoteProfilePublicList)
    {
        if (remoteProfilePublicList == null || localProfileId < 0)
            return null;
        if (remoteProfilePublicList.isEmpty()) return null;

        return new RemoteFoundGameData(localProfileId, remoteProfilePublicList);
    }

    public List<RemoteProfilePublic> getProfilePublicList() {
        return m_remoteProfilePublicList;
    }
}
