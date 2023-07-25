package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public enum NetworkCallbackCommandTypeEnum {
    CONNECTED(),
    FAILURE_OCCURRED(),
    MESSAGE_RECEIVED(),
    DISCONNECTED();

    private NetworkCallbackCommandTypeEnum() {

    }
}
