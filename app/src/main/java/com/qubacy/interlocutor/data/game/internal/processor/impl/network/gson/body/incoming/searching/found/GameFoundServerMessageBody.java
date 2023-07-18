package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;

public class GameFoundServerMessageBody extends ServerMessageBody {
    @SerializedName("foundGameData") public RemoteFoundGameData foundGameData;
}
