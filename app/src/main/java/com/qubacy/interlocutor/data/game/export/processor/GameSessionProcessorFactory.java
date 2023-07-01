package com.qubacy.interlocutor.data.game.export.processor;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessorImpl;

public class GameSessionProcessorFactory {
    protected GameSessionProcessorFactory() {

    }

    public static GameSessionProcessorFactory getInstance() {
        return new GameSessionProcessorFactory();
    }

    public GameSessionProcessor generateGameSessionProcessor() {
        GameSessionProcessorImpl gameSessionProcessor =
                GameSessionProcessorImpl.getInstance();

        return gameSessionProcessor;
    }
}
