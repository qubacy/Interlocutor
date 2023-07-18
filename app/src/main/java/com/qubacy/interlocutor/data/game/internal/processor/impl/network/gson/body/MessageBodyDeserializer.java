package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body;

import com.google.gson.JsonDeserializer;

public abstract class MessageBodyDeserializer<T extends MessageBody>
        implements JsonDeserializer<T>
{

}
