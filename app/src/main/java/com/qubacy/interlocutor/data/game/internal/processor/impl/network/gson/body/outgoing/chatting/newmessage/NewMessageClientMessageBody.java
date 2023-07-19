package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;

public class NewMessageClientMessageBody extends ClientMessageBody {
    public static final String C_MESSAGE_PROP_NAME = "message";

    @SerializedName(C_MESSAGE_PROP_NAME) public Message message;
}
