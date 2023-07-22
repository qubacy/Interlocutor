package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public class NetworkCallbackCommandConnected extends NetworkCallbackCommand {
    protected NetworkCallbackCommandConnected() {
        super();
    }

    public static NetworkCallbackCommandConnected getInstance() {
        return new NetworkCallbackCommandConnected();
    }

    @Override
    public NetworkCallbackCommandTypeEnum getType() {
        return NetworkCallbackCommandTypeEnum.CONNECTED;
    }
}
