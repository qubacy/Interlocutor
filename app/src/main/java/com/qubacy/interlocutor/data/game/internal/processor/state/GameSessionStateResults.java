package com.qubacy.interlocutor.data.game.internal.processor.state;

import java.io.Serializable;

public abstract class GameSessionStateResults
        implements GameSessionState, Serializable
{


    @Override
    public GameSessionStateType getType() {
        return GameSessionStateType.RESULTS;
    }
}
