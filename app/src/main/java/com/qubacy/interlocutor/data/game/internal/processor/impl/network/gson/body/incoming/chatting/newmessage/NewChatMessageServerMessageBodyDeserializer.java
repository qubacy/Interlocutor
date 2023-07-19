package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

import java.lang.reflect.Type;

public class NewChatMessageServerMessageBodyDeserializer
    extends MessageBodyDeserializer<NewChatMessageServerMessageBody>
{
    @Override
    public NewChatMessageServerMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        JsonObject newMessageBodyJsonObj = json.getAsJsonObject();

        if (newMessageBodyJsonObj == null) return null;

        RemoteMessage message =
                deserializeMessage(
                        newMessageBodyJsonObj.getAsJsonObject(
                                NewChatMessageServerMessageBody.C_MESSAGE_PROP_NAME));

        if (message == null) return null;

        return NewChatMessageServerMessageBody.getInstance(message);
    }

    private RemoteMessage deserializeMessage(final JsonObject messageJsonObj) {
        JsonPrimitive senderIdValueJson =
                messageJsonObj.getAsJsonPrimitive(RemoteMessage.C_SENDER_ID_PROP_NAME);
        JsonPrimitive textValueJson =
                messageJsonObj.getAsJsonPrimitive(RemoteMessage.C_TEXT_PROP_NAME);

        if (senderIdValueJson == null || textValueJson == null)
            return null;

        int senderId = senderIdValueJson.getAsInt();
        String text = textValueJson.getAsString();

        return RemoteMessage.getInstance(senderId, text);
    }
}
