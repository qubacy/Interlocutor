package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public class NetworkCallbackCommandFailureOccurred extends NetworkCallbackCommand {
    protected NetworkCallbackCommandFailureOccurred() {

    }

    public static NetworkCallbackCommandFailureOccurred getInstance() {
        return new NetworkCallbackCommandFailureOccurred();
    }

    @Override
    public NetworkCallbackCommandTypeEnum getType() {
        return NetworkCallbackCommandTypeEnum.FAILURE_OCCURRED;
    }
}
