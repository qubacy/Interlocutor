package com.qubacy.interlocutor.data.game.internal.processor.command;

public abstract class Command {

    protected Command() {

    }

    public abstract CommandType getType();
}
