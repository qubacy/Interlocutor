package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;

import java.util.List;

public class UsersChosenClientMessageBody extends ClientMessageBody {
    public static final String C_USER_ID_LIST_PROP_NAME = "userIdList";

    @SerializedName(C_USER_ID_LIST_PROP_NAME) public List<Integer> chosenUserIdList;
}
