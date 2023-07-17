package com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.listener;

import androidx.annotation.NonNull;

import okhttp3.WebSocket;

public class WebSocketListenerImpl extends okhttp3.WebSocketListener {
    private final WebSocketListenerCallback m_callback;

    protected WebSocketListenerImpl(
            final WebSocketListenerCallback callback)
    {
        super();

        m_callback = callback;
    }

    public static WebSocketListenerImpl getInstance(
            final WebSocketListenerCallback callback)
    {
        if (callback == null) return null;

        return new WebSocketListenerImpl(callback);
    }

    @Override
    public void onMessage(
            @NonNull final WebSocket webSocket,
            @NonNull final String text)
    {
        super.onMessage(webSocket, text);

        m_callback.onServerMessageReceived(text);
    }
}
