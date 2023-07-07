package com.qubacy.interlocutor.data.game.internal.processor.implfake.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChoosing;

import java.io.Serializable;

public class GameSessionImplFakeStateChoosing extends GameSessionStateChoosing
        implements Serializable
{


    protected GameSessionImplFakeStateChoosing() {

    }

    public static GameSessionImplFakeStateChoosing getInstance() {
        return new GameSessionImplFakeStateChoosing();
    }


}
