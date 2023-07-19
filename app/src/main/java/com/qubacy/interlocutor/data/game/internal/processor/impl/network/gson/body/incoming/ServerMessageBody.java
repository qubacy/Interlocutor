package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;

public class ServerMessageBody extends MessageBody {
    public static final String C_ERROR_PROP_NAME = "error";

    private final ServerMessageError m_error;

    public ServerMessageBody() {
        m_error = null;
    }

    protected ServerMessageBody(final ServerMessageError serverMessageError) {
        m_error = serverMessageError;
    }

    public static ServerMessageBody getInstance(
            final ServerMessageError serverMessageError)
    {
        if (serverMessageError == null) return null;

        return new ServerMessageBody(serverMessageError);
    }

    public ServerMessageError getError() {
        return m_error;
    }
}
