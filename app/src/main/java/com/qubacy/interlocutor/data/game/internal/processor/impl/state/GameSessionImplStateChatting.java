package com.qubacy.interlocutor.data.game.internal.processor.impl.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChatting;

import java.io.Serializable;

public class GameSessionImplStateChatting extends GameSessionStateChatting
    implements Serializable
{

    protected GameSessionImplStateChatting() {
        super();
    }

    public static GameSessionImplStateChatting getInstance() {
        return new GameSessionImplStateChatting();
    }
}
