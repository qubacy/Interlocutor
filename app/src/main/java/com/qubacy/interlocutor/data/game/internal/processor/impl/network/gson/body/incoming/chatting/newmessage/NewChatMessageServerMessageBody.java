package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

public class NewChatMessageServerMessageBody extends ServerMessageBody {
    public static final String C_MESSAGE_PROP_NAME = "message";

    private final RemoteMessage m_message;

    protected NewChatMessageServerMessageBody(final RemoteMessage message) {
        m_message = message;
    }

    public static NewChatMessageServerMessageBody getInstance(
            final RemoteMessage message)
    {
        if (message == null) return null;

        return new NewChatMessageServerMessageBody(message);
    }

    public RemoteMessage getMessage() {
        return m_message;
    }
}
