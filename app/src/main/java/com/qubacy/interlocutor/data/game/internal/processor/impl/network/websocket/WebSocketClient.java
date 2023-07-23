package com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandConnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandDisconnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandMessageReceived;

import java.util.concurrent.BlockingQueue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    public static final int C_NORMAL_CLOSURE_CODE = 1000;

    private final OkHttpClient m_httpClient;
    private final WebSocket m_webSocket;
    protected final BlockingQueue<NetworkCallbackCommand> m_networkCallbackCommandQueue;

    protected WebSocketClient(
            final OkHttpClient httpClient,
            final WebSocket webSocket,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue)
    {
        m_httpClient = httpClient;
        m_webSocket = webSocket;
        m_networkCallbackCommandQueue = networkCallbackCommandQueue;
    }

    public static WebSocketClient getInstance(
            final String url,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue)
    {
        if (url == null || networkCallbackCommandQueue == null)
        {
            return null;
        }

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder().
                        build();

        if (okHttpClient == null) return null;

        Request request = new Request.Builder().url(url).build();

        if (request == null) return null;

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onClosed(
                    @NonNull WebSocket webSocket,
                    int code,
                    @NonNull String reason)
            {
                super.onClosed(webSocket, code, reason);

                try {
                    networkCallbackCommandQueue.
                            put(NetworkCallbackCommandDisconnected.getInstance());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onMessage(
                    @NonNull WebSocket webSocket,
                    @NonNull String text)
            {
                super.onMessage(webSocket, text);

                try {
                    networkCallbackCommandQueue.
                            put(NetworkCallbackCommandMessageReceived.getInstance(text));

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onOpen(
                    @NonNull WebSocket webSocket,
                    @NonNull Response response)
            {
                super.onOpen(webSocket, response);

                try {
                    networkCallbackCommandQueue.
                            put(NetworkCallbackCommandConnected.getInstance());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        WebSocket webSocket =
                okHttpClient.newWebSocket(request, webSocketListener);

        if (webSocket == null) return null;

        okHttpClient.dispatcher().executorService().shutdown();

        return new WebSocketClient(okHttpClient, webSocket, networkCallbackCommandQueue);
    }

    public boolean sendMessage(final String message) {
        return m_webSocket.send(message);
    }

    public void close() {
        m_webSocket.close(C_NORMAL_CLOSURE_CODE, null);
    }
}
