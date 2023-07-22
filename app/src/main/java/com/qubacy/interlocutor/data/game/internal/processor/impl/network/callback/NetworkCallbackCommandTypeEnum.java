package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public enum NetworkCallbackCommandTypeEnum {
    CONNECTED(),
    MESSAGE_RECEIVED(),
    DISCONNECTED();

    private NetworkCallbackCommandTypeEnum() {

    }
}
