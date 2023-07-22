package com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.incoming.chatting.newmessage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

import java.lang.reflect.Type;

public class ServerMockNewMessageMessageBodyDeserializer
        extends MessageBodyDeserializer<NewMessageClientMessageBody>
{
    @Override
    public NewMessageClientMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        JsonObject messageBodyJsonObj = json.getAsJsonObject();

        if (messageBodyJsonObj == null) return null;

        Message message =
                deserializeNewMessage(messageBodyJsonObj.getAsJsonObject(
                        NewMessageClientMessageBody.C_MESSAGE_PROP_NAME));

        if (message == null) return null;

        return NewMessageClientMessageBody.getInstance(message);
    }

    private Message deserializeNewMessage(
            final JsonObject newMessageJsonObj)
    {
        if (newMessageJsonObj == null) return null;

        JsonPrimitive messageText =
                newMessageJsonObj.getAsJsonPrimitive(Message.C_TEXT_PROP_NAME);

        if (messageText == null) return null;

        return Message.getInstance(messageText.getAsString());
    }
}
