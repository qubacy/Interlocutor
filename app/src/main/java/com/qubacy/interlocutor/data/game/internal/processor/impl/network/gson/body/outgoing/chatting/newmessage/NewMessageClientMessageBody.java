package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;

public class NewMessageClientMessageBody extends ClientMessageBody {
    public static final String C_MESSAGE_PROP_NAME = "message";

    private final Message m_message;

    protected NewMessageClientMessageBody(final Message message) {
        m_message = message;
    }

    public static NewMessageClientMessageBody getInstance(
            final Message message)
    {
        if (message == null) return null;

        return new NewMessageClientMessageBody(message);
    }

    public Message getMessage() {
        return m_message;
    }
}
