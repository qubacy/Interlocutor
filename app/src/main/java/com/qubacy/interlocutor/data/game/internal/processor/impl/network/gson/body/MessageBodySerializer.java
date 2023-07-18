package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body;

import com.google.gson.JsonSerializer;

public abstract class MessageBodySerializer<T extends MessageBody>
        implements JsonSerializer<T>
{

}
