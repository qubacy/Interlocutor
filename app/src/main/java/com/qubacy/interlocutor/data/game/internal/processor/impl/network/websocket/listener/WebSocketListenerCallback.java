package com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.listener;

public interface WebSocketListenerCallback {
    public void onServerMessageReceived(final String serverMessage);
}
