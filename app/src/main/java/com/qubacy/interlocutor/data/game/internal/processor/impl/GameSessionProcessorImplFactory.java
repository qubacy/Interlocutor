package com.qubacy.interlocutor.data.game.internal.processor.impl;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;

public class GameSessionProcessorImplFactory extends GameSessionProcessorFactory {
    @Override
    public GameSessionProcessor generateProcessor() {
        return GameSessionProcessorImpl.getInstance();
    }

    public static GameSessionProcessorImplFactory getInstance() {
        return new GameSessionProcessorImplFactory();
    }
}
