package com.qubacy.interlocutor.data.game.export.struct.message;

import java.io.Serializable;

public class Message implements Serializable {
    final private String m_text;

    protected Message(final String text) {
        m_text = text;
    }

    public static Message getInstance(final String text) {
        if (text == null) return null;
        if (text.isEmpty()) return null;

        return new Message(text);
    }

    public String getText() {
        return m_text;
    }
}
