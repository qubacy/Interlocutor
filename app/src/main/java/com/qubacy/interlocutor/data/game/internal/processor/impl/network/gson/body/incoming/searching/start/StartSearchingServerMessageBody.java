package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

public class StartSearchingServerMessageBody extends ServerMessageBody {
    protected StartSearchingServerMessageBody() {
        super();
    }

    public static StartSearchingServerMessageBody getInstance() {
        return new StartSearchingServerMessageBody();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
