package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer<Message> {
    @Override
    public Message deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        // todo: deserialization..


        return null;
    }
}
