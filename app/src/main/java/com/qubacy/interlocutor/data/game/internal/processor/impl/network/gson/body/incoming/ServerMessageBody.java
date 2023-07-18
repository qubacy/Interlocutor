package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;

public class ServerMessageBody extends MessageBody {
    @SerializedName("error") public ServerMessageError error;
}
