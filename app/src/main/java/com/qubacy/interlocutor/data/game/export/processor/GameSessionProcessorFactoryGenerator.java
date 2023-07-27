package com.qubacy.interlocutor.data.game.export.processor;

import com.qubacy.interlocutor.data.game.internal.processor.impl.GameSessionProcessorImplFactory;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.GameSessionProcessorImplFakeFactory;

public class GameSessionProcessorFactoryGenerator {
    protected GameSessionProcessorFactoryGenerator() {

    }

    public static GameSessionProcessorFactoryGenerator getInstance() {
        return new GameSessionProcessorFactoryGenerator();
    }

    public GameSessionProcessorFactory generateGameSessionProcessor() {
        GameSessionProcessorFactory gameSessionProcessorFactory =
                GameSessionProcessorImplFakeFactory.getInstance(); // todo: get back to a real impl.
                //GameSessionProcessorImplFactory.getInstance();

        return gameSessionProcessorFactory;
    }
}
