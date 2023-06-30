package com.qubacy.interlocutor.data.game.processor.command;

public class CommandStopSearching extends Command {
    protected CommandStopSearching() {
        super();
    }

    public static CommandStopSearching getInstance() {
        return new CommandStopSearching();
    }

    @Override
    public CommandType getType() {
        return CommandType.STOP_SEARCHING;
    }
}
