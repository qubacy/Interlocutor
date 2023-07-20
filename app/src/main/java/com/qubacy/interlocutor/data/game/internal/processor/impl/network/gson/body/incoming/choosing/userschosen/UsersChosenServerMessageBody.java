package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

public class UsersChosenServerMessageBody extends ServerMessageBody {
    protected UsersChosenServerMessageBody() {
        super();
    }

    public static UsersChosenServerMessageBody getInstance() {
        return new UsersChosenServerMessageBody();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
