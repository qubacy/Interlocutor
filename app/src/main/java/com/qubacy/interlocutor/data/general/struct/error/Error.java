package com.qubacy.interlocutor.data.general.struct.error;

import java.io.Serializable;

public class Error implements Serializable {
    private final String m_message;
    private final boolean m_isCritical;

    private Error(
            final String message,
            final boolean isCritical)
    {
        m_message = message;
        m_isCritical = isCritical;
    }

    public static Error getInstance(
            final String message,
            final boolean isCritical)
    {
        if (message == null) return null;
        if (message.isEmpty()) return null;

        return new Error(message, isCritical);
    }

    public String getMessage() {
        return m_message;
    }

    public boolean isCritical() {
        return m_isCritical;
    }
}
