package com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    public static final int C_NORMAL_CLOSURE_CODE = 1000;

    private final OkHttpClient m_httpClient;
    private final WebSocket m_webSocket;

    protected WebSocketClient(
            final OkHttpClient httpClient,
            final WebSocket webSocket)
    {
        m_httpClient = httpClient;
        m_webSocket = webSocket;
    }

    public static WebSocketClient getInstance(
            final String url,
            final WebSocketListener webSocketListener)
    {
        if (url == null || webSocketListener == null)
            return null;

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder().
                        build();

        if (okHttpClient == null) return null;

        Request request = new Request.Builder().url(url).build();

        if (request == null) return null;

        WebSocket webSocket =
                okHttpClient.newWebSocket(request, webSocketListener);

        if (webSocket == null) return null;

        okHttpClient.dispatcher().executorService().shutdown();

        return new WebSocketClient(okHttpClient, webSocket);
    }

    public boolean sendMessage(final String message) {
        return m_webSocket.send(message);
    }

    public void close() {
        m_webSocket.close(C_NORMAL_CLOSURE_CODE, null);
    }
}
