package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming;

import java.util.Objects;

public class ServerMessageError {
    public static final String C_ID_PROP_NAME = "id";

    private final int m_id;

    protected ServerMessageError(
            final int id)
    {
        m_id = id;
    }

    public static ServerMessageError getInstance(
            final int id)
    {
        if (id < 0) return null;

        return new ServerMessageError(id);
    }

    public int getId() {
        return m_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerMessageError that = (ServerMessageError) o;

        return Objects.equals(m_id, that.m_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_id);
    }
}
