package com.qubacy.interlocutor.data.game.internal.processor.implfake;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;

public class GameSessionProcessorImplFakeFactory extends GameSessionProcessorFactory {
    @Override
    public GameSessionProcessor generateProcessor() {
        return GameSessionProcessorImplFake.getInstance();
    }

    public static GameSessionProcessorImplFakeFactory getInstance() {
        return new GameSessionProcessorImplFakeFactory();
    }
}
