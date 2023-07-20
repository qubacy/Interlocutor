package com.qubacy.interlocutor.data.game.export.struct.message;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
    public static final String C_TEXT_PROP_NAME = "text";

    final protected int m_senderId;
    final protected String m_text;

    protected Message(
            final int senderId,
            final String text)
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

    public static Message getInstance(
            final String text)
    {
        if (text == null) return null;
        if (text.isEmpty()) return null;

        return new Message(ProfilePublic.C_DEFAULT_LOCAL_ID, text);
    }

    public String getText() {
        return m_text;
    }

    public int getSenderId() {
        return m_senderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return m_senderId == message.m_senderId &&
                Objects.equals(m_text, message.m_text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_senderId, m_text);
    }
}
