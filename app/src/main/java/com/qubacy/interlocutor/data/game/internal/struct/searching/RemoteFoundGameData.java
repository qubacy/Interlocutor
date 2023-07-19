package com.qubacy.interlocutor.data.game.internal.struct.searching;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameDataBase;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.List;

public class RemoteFoundGameData extends FoundGameDataBase {
    public static final String C_LOCAL_PROFILE_ID_PROP_NAME = "localProfileId";
    public static final String C_START_SESSION_TIME_PROP_NAME = "startSessionTime";
    public static final String C_CHATTING_STAGE_DURATION_PROP_NAME = "chattingStageDuration";
    public static final String C_CHOOSING_STAGE_DURATION_PROP_NAME = "choosingStageDuration";
    public static final String C_CHATTING_TOPIC_PROP_NAME = "chattingTopic";
    public static final String C_PROFILE_PUBLIC_LIST = "profilePublicList";

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
