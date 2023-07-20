package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.stop;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

public class StopSearchingServerMessageBody extends ServerMessageBody {
    protected StopSearchingServerMessageBody() {
        super();
    }

    public static StopSearchingServerMessageBody getInstance() {
        return new StopSearchingServerMessageBody();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
