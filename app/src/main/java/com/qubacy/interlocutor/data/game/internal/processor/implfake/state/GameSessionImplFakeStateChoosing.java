package com.qubacy.interlocutor.data.game.internal.processor.implfake.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChoosing;

import java.io.Serializable;
import java.util.List;

public class GameSessionImplFakeStateChoosing extends GameSessionStateChoosing
        implements Serializable
{
    private List<Integer> m_chosenUserIdList;

    protected GameSessionImplFakeStateChoosing() {

    }

    public static GameSessionImplFakeStateChoosing getInstance() {
        return new GameSessionImplFakeStateChoosing();
    }

    public boolean setChosenUserIdList(
            final List<Integer> chosenUserIdList)
    {
        if (chosenUserIdList == null) return false;
        if (chosenUserIdList.isEmpty()) return false;

        m_chosenUserIdList = chosenUserIdList;

        return true;
    }

    public List<Integer> getChosenUserIdList() {
        return m_chosenUserIdList;
    }
}
