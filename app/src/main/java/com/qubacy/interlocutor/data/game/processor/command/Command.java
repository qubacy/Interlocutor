package com.qubacy.interlocutor.data.game.processor.command;

public abstract class Command {

    protected Command() {

    }

    public abstract CommandType getType();
}
