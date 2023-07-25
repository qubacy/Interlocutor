package com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandConnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandDisconnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandFailureOccurred;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandMessageReceived;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private final AtomicBoolean m_isClosed;

    protected WebSocketClient(
            final OkHttpClient httpClient,
            final WebSocket webSocket,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final AtomicBoolean isClosed)
    {
        m_httpClient = httpClient;
        m_webSocket = webSocket;
        m_networkCallbackCommandQueue = networkCallbackCommandQueue;
        m_isClosed = isClosed;
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

        AtomicBoolean isClosed = new AtomicBoolean(false);

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onClosed(
                    @NonNull WebSocket webSocket,
                    int code,
                    @NonNull String reason)
            {
                super.onClosed(webSocket, code, reason);

                isClosed.set(true);

                Log.d("TEST", "onClosed(); reason: " + reason);

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

                Log.d("TEST", "onMessage(); message: " + text);

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

                Log.d("TEST", "onOpen()");

                try {
                    networkCallbackCommandQueue.
                            put(NetworkCallbackCommandConnected.getInstance());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(
                    @NonNull WebSocket webSocket,
                    @NonNull Throwable t,
                    @Nullable Response response)
            {
                super.onFailure(webSocket, t, response);

                isClosed.set(true);

                Log.d("TEST", "onFailure(); exception: " + (t.getMessage() == null ? "" : t.getMessage()));

                try {
                    networkCallbackCommandQueue.
                            put(NetworkCallbackCommandFailureOccurred.getInstance());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);

                isClosed.set(true);

                Log.d("TEST", "onClosing(); reason: " + reason + "; code: " + String.valueOf(code));

                webSocket.close(code, "");
            }
        };

        WebSocket webSocket =
                okHttpClient.newWebSocket(request, webSocketListener);

        if (webSocket == null) return null;

        okHttpClient.dispatcher().executorService().shutdown();

        return new WebSocketClient(okHttpClient, webSocket, networkCallbackCommandQueue, isClosed);
    }

    public boolean sendMessage(final String message) {
        if (m_isClosed.get() == true) return true;

        return m_webSocket.send(message);
    }

    public void close() {
        if (m_isClosed.get() == true) return;

        // todo: it's not really a graceful closure. Think of it..

        m_webSocket.close(C_NORMAL_CLOSURE_CODE, null);
    }
}
