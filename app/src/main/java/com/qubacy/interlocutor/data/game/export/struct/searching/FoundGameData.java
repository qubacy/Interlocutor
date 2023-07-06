package com.qubacy.interlocutor.data.game.export.struct.searching;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import java.io.Serializable;
import java.util.List;

public class FoundGameData extends FoundGameDataBase implements Serializable {
    protected final List<ProfilePublic> m_profilePublicList;

    protected FoundGameData(
            final int localProfileId,
            final long startSessionTime,
            final long chattingStageDuration,
            final long choosingStageDuration,
            final String chattingTopic,
            final List<ProfilePublic> profilePublicList)
    {
        super(localProfileId, startSessionTime,
                chattingStageDuration, choosingStageDuration, chattingTopic);

        m_profilePublicList = profilePublicList;
    }

    public static FoundGameData getInstance(
            final int localProfileId,
            final long startSessionTime,
            final long chattingStageDuration,
            final long choosingStageDuration,
            final String chattingTopic,
            final List<ProfilePublic> profilePublicList)
    {
        if (profilePublicList == null || localProfileId < 0 ||
            startSessionTime < 0 || chattingStageDuration <= 0 ||
            choosingStageDuration <= 0 || chattingTopic == null)
        {
            return null;
        }

        if (profilePublicList.isEmpty() || chattingTopic.isEmpty())
            return null;

        return new FoundGameData(
                localProfileId,
                startSessionTime,
                chattingStageDuration,
                choosingStageDuration,
                chattingTopic,
                profilePublicList);
    }

    public List<ProfilePublic> getProfilePublicList() {
        return m_profilePublicList;
    }
}
