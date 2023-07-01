package com.qubacy.interlocutor.data.game.internal.struct.message;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;

import java.io.Serializable;

public class RemoteMessage extends Message implements Serializable {
    protected int m_senderId;

    protected RemoteMessage(final String text, final int senderId) {
        super(text);
    }

    public static RemoteMessage getInstance(
            final String text,
            final int senderId)
    {
        if (text == null) return null;
        if (text.isEmpty()) return null;

        return new RemoteMessage(text, senderId);
    }

    public int getSenderId() {
        return m_senderId;
    }
}
