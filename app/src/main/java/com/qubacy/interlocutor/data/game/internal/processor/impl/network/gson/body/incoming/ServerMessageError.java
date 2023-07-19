package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming;

public class ServerMessageError {
    public static final String C_MESSAGE_PROP_NAME = "message";

    private final String m_message;

    protected ServerMessageError(final String message) {
        m_message = message;
    }

    public static ServerMessageError getInstance(final String message) {
        if (message == null) return null;

        return new ServerMessageError(message);
    }

    public String getMessage() {
        return m_message;
    }
}
