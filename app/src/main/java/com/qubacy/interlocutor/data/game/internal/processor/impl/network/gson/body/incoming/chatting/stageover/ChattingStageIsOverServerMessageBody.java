package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;

public class ChattingStageIsOverServerMessageBody extends ServerMessageBody {
    protected ChattingStageIsOverServerMessageBody() {
        super();
    }

    public static ChattingStageIsOverServerMessageBody getInstance() {
        return new ChattingStageIsOverServerMessageBody();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
