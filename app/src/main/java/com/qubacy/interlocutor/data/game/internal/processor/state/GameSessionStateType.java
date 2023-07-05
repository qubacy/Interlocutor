package com.qubacy.interlocutor.data.game.internal.processor.state;

public enum GameSessionStateType {
    SEARCHING(),
    CHATTING(),
    CHOOSING(),
    ENDING();

    private GameSessionStateType() {

    }
}
