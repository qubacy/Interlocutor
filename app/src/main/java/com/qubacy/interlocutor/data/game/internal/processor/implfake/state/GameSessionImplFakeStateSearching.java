package com.qubacy.interlocutor.data.game.internal.processor.implfake.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateSearching;

import java.io.Serializable;

public class GameSessionImplFakeStateSearching extends GameSessionStateSearching
    implements Serializable
{
    private long m_startSearchingTime = 0;

    protected GameSessionImplFakeStateSearching(final long startSearchingTime) {
        m_startSearchingTime = startSearchingTime;
    }

    public static GameSessionImplFakeStateSearching getInstance(final long startSearchingTime) {
        if (startSearchingTime < 0) return null;

        return new GameSessionImplFakeStateSearching(startSearchingTime);
    }

    public long getStartSearchingTime() {
        return m_startSearchingTime;
    }
}
