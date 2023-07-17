package com.qubacy.interlocutor.data.game.export.processor;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;

import java.io.Serializable;

public abstract class GameSessionProcessorFactory
    implements
        Serializable
{
    protected GameSessionProcessorFactory() {

    }

    public abstract GameSessionProcessor generateProcessor();
}
