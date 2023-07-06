package com.qubacy.interlocutor.data.game.internal.processor.state;

import java.io.Serializable;

public abstract class GameSessionStateChoosing
        implements GameSessionState, Serializable
{

    @Override
    public GameSessionStateType getType() {
        return GameSessionStateType.CHOOSING;
    }
}
