package com.qubacy.interlocutor.data.game.internal.processor.impl;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.WebSocketClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameSessionProcessorImplFactory extends GameSessionProcessorFactory {
    @Override
    public GameSessionProcessor generateProcessor() {
        BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue =
                new LinkedBlockingQueue<>();
        WebSocketClient webSocketClient =
                WebSocketClient.getInstance(
                        GameSessionProcessorImpl.C_URL,
                        networkCallbackCommandQueue);

        return GameSessionProcessorImpl.getInstance(
                networkCallbackCommandQueue, webSocketClient);
    }

    public static GameSessionProcessorImplFactory getInstance() {
        return new GameSessionProcessorImplFactory();
    }
}
