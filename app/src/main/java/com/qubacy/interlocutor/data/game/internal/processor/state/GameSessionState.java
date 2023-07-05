package com.qubacy.interlocutor.data.game.internal.processor.state;

import java.io.Serializable;

public interface GameSessionState extends Serializable {
    public GameSessionStateType getType();
}
