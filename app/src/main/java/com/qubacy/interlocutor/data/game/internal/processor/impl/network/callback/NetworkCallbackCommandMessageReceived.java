package com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback;

public class NetworkCallbackCommandMessageReceived extends NetworkCallbackCommand {
    private final String m_message;

    protected NetworkCallbackCommandMessageReceived(final String message) {
        super();

        m_message = message;
    }

    public static NetworkCallbackCommandMessageReceived getInstance(
            final String message)
    {
        if (message == null) return null;

        return new NetworkCallbackCommandMessageReceived(message);
    }

    @Override
    public NetworkCallbackCommandTypeEnum getType() {
        return NetworkCallbackCommandTypeEnum.MESSAGE_RECEIVED;
    }

    public String getMessage() {
        return m_message;
    }
}
