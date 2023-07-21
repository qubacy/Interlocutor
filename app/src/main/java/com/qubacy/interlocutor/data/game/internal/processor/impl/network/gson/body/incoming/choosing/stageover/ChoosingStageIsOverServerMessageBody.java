package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

import java.util.List;
import java.util.Objects;

public class ChoosingStageIsOverServerMessageBody extends ServerMessageBody {
    public static final String C_MATCHED_USERS_PROP_NAME = "matchedUsers";

    private final List<MatchedUserProfileData> m_matchedUserDataList;

    protected ChoosingStageIsOverServerMessageBody(
            final List<MatchedUserProfileData> matchedUserDataList)
    {
        m_matchedUserDataList = matchedUserDataList;
    }

    public static ChoosingStageIsOverServerMessageBody getInstance(
            final List<MatchedUserProfileData> matchedUserDataList)
    {
        if (matchedUserDataList == null) return null;

        return new ChoosingStageIsOverServerMessageBody(matchedUserDataList);
    }

    public List<MatchedUserProfileData> getMatchedUsers() {
        return m_matchedUserDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ChoosingStageIsOverServerMessageBody that = (ChoosingStageIsOverServerMessageBody) o;

        return Objects.equals(m_matchedUserDataList, that.m_matchedUserDataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), m_matchedUserDataList);
    }
}
