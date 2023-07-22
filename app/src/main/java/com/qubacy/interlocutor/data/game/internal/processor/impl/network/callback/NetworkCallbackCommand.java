package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public abstract class NetworkCallbackCommand {
    protected NetworkCallbackCommand() {

    }

    public abstract NetworkCallbackCommandTypeEnum getType();
}
