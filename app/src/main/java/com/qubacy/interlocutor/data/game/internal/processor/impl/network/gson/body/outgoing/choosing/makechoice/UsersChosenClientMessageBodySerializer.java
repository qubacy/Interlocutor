package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;

import java.lang.reflect.Type;

public class UsersChosenClientMessageBodySerializer
        extends MessageBodySerializer<MessageBody>
{

    @Override
    public JsonElement serialize(
            final MessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        // todo: serialization..

        return null;
    }
}
