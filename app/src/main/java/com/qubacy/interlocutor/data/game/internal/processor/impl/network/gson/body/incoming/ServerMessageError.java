package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerMessageError that = (ServerMessageError) o;

        return Objects.equals(m_message, that.m_message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_message);
    }
}
