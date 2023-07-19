package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;

import java.lang.reflect.Type;

public class NewMessageClientMessageBodySerializer
        extends MessageBodySerializer<NewMessageClientMessageBody>
{

    @Override
    public JsonElement serialize(
            final NewMessageClientMessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        JsonObject newMessageJsonObj = serializeMessage(src.message);

        if (newMessageJsonObj == null) return null;

        JsonObject newMessageBodyJsonObj = new JsonObject();

        newMessageBodyJsonObj.add(
                NewMessageClientMessageBody.C_MESSAGE_PROP_NAME, newMessageJsonObj);

        return newMessageBodyJsonObj;
    }

    private JsonObject serializeMessage(final Message message) {
        if (message == null) return null;

        JsonObject messageJsonObj = new JsonObject();
        JsonPrimitive messageTextJson = new JsonPrimitive(message.getText());

        messageJsonObj.add(Message.C_TEXT_PROP_NAME, messageTextJson);

        return messageJsonObj;
    }
}
