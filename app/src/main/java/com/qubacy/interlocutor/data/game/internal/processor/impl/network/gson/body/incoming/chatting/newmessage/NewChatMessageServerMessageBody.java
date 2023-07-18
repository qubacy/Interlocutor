package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

public class NewChatMessageServerMessageBody extends ServerMessageBody {
    @SerializedName("message") public RemoteMessage message;
}
