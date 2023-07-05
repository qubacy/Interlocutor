package com.qubacy.interlocutor.data.game.export.processor;

import com.qubacy.interlocutor.data.game.internal.processor.impl.GameSessionProcessorImpl;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.GameSessionProcessorImplFake;

public class GameSessionProcessorFactory {
    protected GameSessionProcessorFactory() {

    }

    public static GameSessionProcessorFactory getInstance() {
        return new GameSessionProcessorFactory();
    }

    public GameSessionProcessor generateGameSessionProcessor() {
        GameSessionProcessor gameSessionProcessor =
                GameSessionProcessorImplFake.getInstance(); // todo: get back to a real impl.
//                GameSessionProcessorImpl.getInstance();

        return gameSessionProcessor;
    }
}
