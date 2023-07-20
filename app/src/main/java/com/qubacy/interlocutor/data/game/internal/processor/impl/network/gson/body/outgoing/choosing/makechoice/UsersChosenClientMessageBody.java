package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;

import java.util.List;

public class UsersChosenClientMessageBody extends ClientMessageBody {
    public static final String C_USER_ID_LIST_PROP_NAME = "userIdList";

    private final List<Integer> m_chosenUserIdList;

    protected UsersChosenClientMessageBody(
            final List<Integer> chosenUserIdList)
    {
        m_chosenUserIdList = chosenUserIdList;
    }

    public static UsersChosenClientMessageBody getInstance(
            final List<Integer> chosenUserIdList)
    {
        if (chosenUserIdList == null) return null;

        return new UsersChosenClientMessageBody(chosenUserIdList);
    }

    public List<Integer> getChosenUserIdList() {
        return m_chosenUserIdList;
    }
}
