package com.qubacy.interlocutor.data.game.internal.struct.searching;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameDataBase;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.List;

public class RemoteFoundGameData extends FoundGameDataBase {
    final private List<RemoteProfilePublic> m_remoteProfilePublicList;

    protected RemoteFoundGameData(
            final int localProfileId,
            final long startSessionTime,
            final long chattingStageDuration,
            final long choosingStageDuration,
            final String chattingTopic,
            final List<RemoteProfilePublic> remoteProfilePublicList)
    {
        super(localProfileId, startSessionTime, chattingStageDuration,
                choosingStageDuration, chattingTopic);

        m_remoteProfilePublicList = remoteProfilePublicList;
    }

    public static RemoteFoundGameData getInstance(
            final int localProfileId,
            final long startSessionTime,
            final long chattingStageDuration,
            final long choosingStageDuration,
            final String chattingTopic,
            final List<RemoteProfilePublic> remoteProfilePublicList)
    {
        if (remoteProfilePublicList == null || localProfileId < 0 || chattingTopic == null)
            return null;
        if (remoteProfilePublicList.isEmpty() || chattingTopic.isEmpty())
            return null;

        return new RemoteFoundGameData(
                localProfileId,
                startSessionTime,
                chattingStageDuration,
                choosingStageDuration,
                chattingTopic,
                remoteProfilePublicList);
    }

    public List<RemoteProfilePublic> getProfilePublicList() {
        return m_remoteProfilePublicList;
    }
}
