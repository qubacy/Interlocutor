package com.qubacy.interlocutor.data.game.internal.processor.impl.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateSearching;

import java.io.Serializable;

public class GameSessionImplStateSearching extends GameSessionStateSearching
    implements Serializable
{
    protected GameSessionImplStateSearching() {

    }

    public static GameSessionImplStateSearching getInstance() {
        return new GameSessionImplStateSearching();
    }
}
