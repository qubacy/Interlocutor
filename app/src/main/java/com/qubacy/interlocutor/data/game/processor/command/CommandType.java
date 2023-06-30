package com.qubacy.interlocutor.data.game.processor.command;

public enum CommandType {
    START_SEARCHING(),
    STOP_SEARCHING(),
    SEND_MESSAGE(),
    CHOOSE_USERS(),
    LEAVE();

    private CommandType() {

    }
}
