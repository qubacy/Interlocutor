package com.qubacy.interlocutor.data.game.internal.struct.message;

import androidx.annotation.Nullable;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;

import java.io.Serializable;

public class RemoteMessage extends Message implements Serializable {
    public static final String C_SENDER_ID_PROP_NAME = "senderId";

    protected RemoteMessage(
            final int senderId, final String text)
    {
        super(senderId, text);
    }

    public static RemoteMessage getInstance(
            final int senderId,
            final String text)
    {
        if (text == null) return null;
        if (text.isEmpty()) return null;

        return new RemoteMessage(senderId, text);
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        return super.equals(obj);
    }
}
