package com.qubacy.interlocutor.data.game.internal.processor.state;

import java.io.Serializable;

public abstract class GameSessionStateChatting
    implements
        GameSessionState, Serializable
{
    protected GameSessionStateChatting() {

    }

    @Override
    public GameSessionStateType getType() {
        return GameSessionStateType.CHATTING;
    }

}
