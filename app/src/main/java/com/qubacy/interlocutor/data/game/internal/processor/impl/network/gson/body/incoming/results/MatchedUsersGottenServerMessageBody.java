package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.results;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

import java.util.List;

public class MatchedUsersGottenServerMessageBody extends ServerMessageBody {
    public List<MatchedUserProfileData> matchedUserDataList;
}
