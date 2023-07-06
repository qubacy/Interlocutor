package com.qubacy.interlocutor.data.game.internal.struct.message;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.internal.struct.DataMapper;

public class RemoteMessageDataMapper implements DataMapper<RemoteMessage, Message> {
    protected RemoteMessageDataMapper() {

    }

    public static RemoteMessageDataMapper getInstance() {
        return new RemoteMessageDataMapper();
    }

    @Override
    public Message map(final RemoteMessage input) {
        if (input == null) return null;

        return Message.getInstance(input.getSenderId(), input.getText());
    }
}
