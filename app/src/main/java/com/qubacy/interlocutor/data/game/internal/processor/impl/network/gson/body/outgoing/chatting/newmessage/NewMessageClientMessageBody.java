package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

public class NewMessageClientMessageBody extends ClientMessageBody {
    @SerializedName("message") public RemoteMessage message;
}
