package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming;

import com.google.gson.annotations.SerializedName;

public class ServerMessageError {
    @SerializedName("message") public String message;
}
