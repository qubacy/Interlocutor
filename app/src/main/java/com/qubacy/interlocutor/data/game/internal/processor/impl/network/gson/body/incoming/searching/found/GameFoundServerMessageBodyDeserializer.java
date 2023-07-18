package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;

import java.lang.reflect.Type;

public class GameFoundServerMessageBodyDeserializer
        extends MessageBodyDeserializer<GameFoundServerMessageBody>
{

    @Override
    public GameFoundServerMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        // todo: deserialization..

        return null;
    }
}
