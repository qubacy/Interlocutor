package com.qubacy.interlocutor.data.game.internal.processor.impl.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateResults;

public class GameSessionImplStateResults extends GameSessionStateResults {
    protected GameSessionImplStateResults() {
        super();
    }

    public static GameSessionImplStateResults getInstance() {
        return new GameSessionImplStateResults();
    }
}
