package com.qubacy.interlocutor.data.game.internal.processor.impl.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChoosing;

public class GameSessionImplStateChoosing extends GameSessionStateChoosing {
    protected GameSessionImplStateChoosing() {
        super();

    }

    public static GameSessionImplStateChoosing getInstance() {
        return new GameSessionImplStateChoosing();
    }
}
