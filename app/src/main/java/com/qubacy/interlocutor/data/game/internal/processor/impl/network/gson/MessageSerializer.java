package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MessageSerializer implements JsonSerializer<Message> {

    @Override
    public JsonElement serialize(
            final Message src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        //todo: serialization...

        return null;
    }
}
