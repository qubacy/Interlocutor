package com.qubacy.interlocutor.data.game.internal.processor.command;

public class CommandLeave extends Command {
    protected CommandLeave() {
        super();
    }

    public static CommandLeave getInstance() {
        return new CommandLeave();
    }

    @Override
    public CommandType getType() {
        return CommandType.LEAVE;
    }
}
