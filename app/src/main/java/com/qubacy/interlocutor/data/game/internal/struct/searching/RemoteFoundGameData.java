package com.qubacy.interlocutor.data.game.internal.struct.searching;

import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.List;

public class RemoteFoundGameData {
    final private List<RemoteProfilePublic> m_remoteProfilePublicList;

    protected RemoteFoundGameData(
            final List<RemoteProfilePublic> remoteProfilePublicList)
    {
        m_remoteProfilePublicList = remoteProfilePublicList;
    }

    public static RemoteFoundGameData getInstance(
            final List<RemoteProfilePublic> remoteProfilePublicList)
    {
        if (remoteProfilePublicList == null) return null;
        if (remoteProfilePublicList.isEmpty()) return null;

        return new RemoteFoundGameData(remoteProfilePublicList);
    }

    public List<RemoteProfilePublic> getProfilePublicList() {
        return m_remoteProfilePublicList;
    }
}
