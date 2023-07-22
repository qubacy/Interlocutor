package com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.outgoing.chatting.newmessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

import java.lang.reflect.Type;

public class ServerMockNewMessageMessageBodySerializer
        extends MessageBodySerializer<NewChatMessageServerMessageBody>
{

    @Override
    public JsonElement serialize(
            final NewChatMessageServerMessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        JsonObject newMessageJsonObj = serializeMessage(src.getMessage());

        if (newMessageJsonObj == null) return null;

        JsonObject messageBodyJsonObj = new JsonObject();

        messageBodyJsonObj.add(
                NewChatMessageServerMessageBody.C_MESSAGE_PROP_NAME, messageBodyJsonObj);

        return messageBodyJsonObj;
    }

    private JsonObject serializeMessage(final RemoteMessage message) {
        if (message == null) return null;

        JsonPrimitive senderIdValueJson = new JsonPrimitive(message.getSenderId());
        JsonPrimitive textValueJson = new JsonPrimitive(message.getText());
        JsonObject messageJsonObj = new JsonObject();

        messageJsonObj.add(RemoteMessage.C_SENDER_ID_PROP_NAME, senderIdValueJson);
        messageJsonObj.add(RemoteMessage.C_TEXT_PROP_NAME, textValueJson);

        return messageJsonObj;
    }
}
