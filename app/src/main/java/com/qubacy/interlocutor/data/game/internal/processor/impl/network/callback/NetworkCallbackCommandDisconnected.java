package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public class NetworkCallbackCommandDisconnected extends NetworkCallbackCommand {

    protected NetworkCallbackCommandDisconnected() {
        super();

    }

    public static NetworkCallbackCommandDisconnected getInstance() {
        return new NetworkCallbackCommandDisconnected();
    }

    @Override
    public NetworkCallbackCommandTypeEnum getType() {
        return NetworkCallbackCommandTypeEnum.DISCONNECTED;
    }
}
