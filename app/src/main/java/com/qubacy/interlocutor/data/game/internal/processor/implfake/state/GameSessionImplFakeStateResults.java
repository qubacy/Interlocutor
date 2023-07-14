package com.qubacy.interlocutor.data.game.internal.processor.implfake.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateResults;

public class GameSessionImplFakeStateResults extends GameSessionStateResults {
    protected GameSessionImplFakeStateResults() {
        super();
    }

    public static GameSessionImplFakeStateResults getInstance() {
        return new GameSessionImplFakeStateResults();
    }
}
