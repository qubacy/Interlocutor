package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.results;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

public class ResultsGottenServerMessageBody extends ServerMessageBody {
    @SerializedName("matchedUsers") public MatchedUsersGottenServerMessageBody matchedUsers;
}
