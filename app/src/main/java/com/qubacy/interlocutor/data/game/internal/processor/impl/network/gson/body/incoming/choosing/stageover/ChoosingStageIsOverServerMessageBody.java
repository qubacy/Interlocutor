package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

public class ChoosingStageIsOverServerMessageBody extends ServerMessageBody {
    protected ChoosingStageIsOverServerMessageBody() {
        super();
    }

    public static ChoosingStageIsOverServerMessageBody getInstance() {
        return new ChoosingStageIsOverServerMessageBody();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
