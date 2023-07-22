package com.qubacy.interlocutor.data.game.internal.processor.network.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.stop.StopSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.incoming.chatting.newmessage.ServerMockNewMessageMessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.incoming.choosing.userschosen.ServerMockUsersChosenMessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.incoming.searching.start.ServerMockStartSearchingMessageBodyDeserializer;

import java.lang.reflect.Type;

public class ServerMockMessageDeserializer implements JsonDeserializer<Message> {
    private Type m_curType = null;
    private JsonDeserializationContext m_curContext = null;

    @Override
    public Message deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        m_curType = typeOfT;
        m_curContext = context;

        JsonObject rootJsonObj = json.getAsJsonObject();

        if (rootJsonObj == null) return null;

        OperationEnum operation =
                deserializeOperation(
                        rootJsonObj.getAsJsonPrimitive(Message.C_OPERATION_PROP_NAME));

        if (operation == null) return null;

        ClientMessageBody body =
                deserializeBody(
                        operation,
                        rootJsonObj.getAsJsonObject(Message.C_BODY_PROP_NAME));

        if (body == null) return null;

        return Message.getInstance(operation, body);
    }

    private OperationEnum deserializeOperation(final JsonPrimitive operationIdValueJson) {
        if (operationIdValueJson == null) return null;

        int operationId = operationIdValueJson.getAsInt();

        return OperationEnum.getOperationById(operationId);
    }

    private ClientMessageBody deserializeBody(
            final OperationEnum operation,
            final JsonObject messageBodyJsonObj)
    {
        if (messageBodyJsonObj == null) return null;

        switch (operation) {
            case SEARCHING_START: return deserializeStartSearchingBody(messageBodyJsonObj);
            case SEARCHING_STOP: return deserializeStopSearchingBody(messageBodyJsonObj);
            case CHATTING_NEW_MESSAGE: return deserializeNewMessageBody(messageBodyJsonObj);
            case CHOOSING_USERS_CHOSEN: return deserializeUsersChosen(messageBodyJsonObj);
        }

        return null;
    }

    private StartSearchingClientMessageBody deserializeStartSearchingBody(
            final JsonObject messageBodyJsonObj)
    {
        ServerMockStartSearchingMessageBodyDeserializer deserializer =
                new ServerMockStartSearchingMessageBodyDeserializer();

        return deserializer.deserialize(messageBodyJsonObj, m_curType, m_curContext);
    }

    private StopSearchingClientMessageBody deserializeStopSearchingBody(
            final JsonObject messageBodyJsonObj)
    {
        return new StopSearchingClientMessageBody();
    }

    private NewMessageClientMessageBody deserializeNewMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        ServerMockNewMessageMessageBodyDeserializer deserializer =
                new ServerMockNewMessageMessageBodyDeserializer();

        return deserializer.deserialize(messageBodyJsonObj, m_curType, m_curContext);
    }

    private UsersChosenClientMessageBody deserializeUsersChosen(
            final JsonObject messageBodyJsonObj)
    {
        ServerMockUsersChosenMessageBodyDeserializer deserializer =
                new ServerMockUsersChosenMessageBodyDeserializer();

        return deserializer.deserialize(messageBodyJsonObj, m_curType, m_curContext);
    }
}
