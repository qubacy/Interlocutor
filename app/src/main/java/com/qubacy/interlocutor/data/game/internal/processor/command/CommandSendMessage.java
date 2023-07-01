package com.qubacy.interlocutor.data.game.internal.processor.command;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;

public class CommandSendMessage extends Command {
    private final Message m_message;

    protected CommandSendMessage(final Message message) {
        super();

        m_message = message;
    }

    public static CommandSendMessage getInstance(final Message message) {
        if (message == null) return null;

        return new CommandSendMessage(message);
    }

    @Override
    public CommandType getType() {
        return CommandType.SEND_MESSAGE;
    }

    public Message getMessage() {
        return m_message;
    }
}
