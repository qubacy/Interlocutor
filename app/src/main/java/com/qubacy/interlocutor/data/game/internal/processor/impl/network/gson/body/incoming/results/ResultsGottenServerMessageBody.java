package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.results;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

import java.util.List;

public class ResultsGottenServerMessageBody extends ServerMessageBody {
    public static final String C_MATCHED_USERS_PROP_NAME = "matchedUsers";

    private final List<MatchedUserProfileData> m_matchedUserDataList;

    protected ResultsGottenServerMessageBody(
            final List<MatchedUserProfileData> matchedUserDataList)
    {
        m_matchedUserDataList = matchedUserDataList;
    }

    public static ResultsGottenServerMessageBody getInstance(
            final List<MatchedUserProfileData> matchedUserDataList)
    {
        if (matchedUserDataList == null) return null;

        return new ResultsGottenServerMessageBody(matchedUserDataList);
    }

    public List<MatchedUserProfileData> getMatchedUsers() {
        return m_matchedUserDataList;
    }
}
