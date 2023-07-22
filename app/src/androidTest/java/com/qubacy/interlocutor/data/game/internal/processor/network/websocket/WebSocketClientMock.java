package com.qubacy.interlocutor.data.game.internal.processor.network.websocket;

import android.os.SystemClock;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.WebSocketClient;

import java.util.concurrent.BlockingQueue;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class WebSocketClientMock extends WebSocketClient {
    public static final long C_TIMEOUT_MILLISECONDS = 400;

    private Thread m_thread = null;

    protected WebSocketClientMock(
            final OkHttpClient httpClient,
            final WebSocket webSocket,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue)
    {
        super(httpClient, webSocket, networkCallbackCommandQueue);
    }

    public static WebSocketClientMock getInstance(
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue)
    {
        if (networkCallbackCommandQueue == null) return null;

        return new WebSocketClientMock(
                null, null, networkCallbackCommandQueue);
    }

    public void launch() {
        m_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                exec();
            }
        });

        m_thread.start();
    }

    public void stop() {
        if (m_thread == null) return;

        m_thread.interrupt();
    }

    protected void exec() {
        while (!Thread.interrupted()) {
            SystemClock.sleep(C_TIMEOUT_MILLISECONDS);

            if (!execIteration()) break;
        }
    }

    protected boolean execIteration() {


        return true;
    }

    @Override
    public boolean sendMessage(final String message) {


        return false;
    }
}
