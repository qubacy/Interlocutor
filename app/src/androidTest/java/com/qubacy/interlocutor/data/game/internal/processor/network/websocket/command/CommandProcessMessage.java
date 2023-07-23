package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.command;

public class CommandProcessMessage extends Command {
    private final String m_message;

    protected CommandProcessMessage(final String message) {
        m_message = message;
    }

    public static CommandProcessMessage getInstance(
            final String message)
    {
        if (message == null) return null;

        return new CommandProcessMessage(message);
    }

    @Override
    public CommandTypeEnum getType() {
        return CommandTypeEnum.PROCESS_MESSAGE;
    }

    public String getMessage() {
        return m_message;
    }
}
