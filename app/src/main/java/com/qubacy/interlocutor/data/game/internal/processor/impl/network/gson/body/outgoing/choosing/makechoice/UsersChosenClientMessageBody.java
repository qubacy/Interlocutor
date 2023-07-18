package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice;

import com.google.gson.annotations.SerializedName;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;

import java.util.List;

public class UsersChosenClientMessageBody extends ClientMessageBody {
    @SerializedName("userIdList") public List<Integer> chosenUserIdList;
}
