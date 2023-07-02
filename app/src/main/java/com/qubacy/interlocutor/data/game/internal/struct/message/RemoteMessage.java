package com.qubacy.interlocutor.data.game.internal.struct.message;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;

import java.io.Serializable;

public class RemoteMessage extends Message implements Serializable {
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
}
