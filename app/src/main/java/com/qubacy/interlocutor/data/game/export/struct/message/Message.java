package com.qubacy.interlocutor.data.game.export.struct.message;

import java.io.Serializable;

public class Message implements Serializable {
    final protected int m_senderId;
    final protected String m_text;

    protected Message(
            final int senderId, final String text)
    {
        m_senderId = senderId;
        m_text = text;
    }

    public static Message getInstance(
            final int senderId,
            final String text)
    {
        if (text == null) return null;
        if (text.isEmpty()) return null;

        return new Message(senderId, text);
    }

    public String getText() {
        return m_text;
    }

    public int getSenderId() {
        return m_senderId;
    }
}
