package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageError;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover.ChattingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen.UsersChosenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;

import java.lang.reflect.Type;

public class MessageDeserializer implements JsonDeserializer<Message> {
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

        JsonObject rootJsonObj = json.getAsJsonObject();

        if (rootJsonObj == null) return null;

        OperationEnum operation =
                deserializeOperation(rootJsonObj.get(Message.C_OPERATION_PROP_NAME));

        if (operation == null) return null;

        ServerMessageBody messageBody =
                deserializeMessageBody(
                        operation, rootJsonObj.getAsJsonObject(Message.C_BODY_PROP_NAME));

        if (messageBody == null) return null;

        return Message.getInstance(operation, messageBody);
    }

    private OperationEnum deserializeOperation(
            final JsonElement operationJsonElem)
    {
        if (operationJsonElem == null) return null;

        JsonPrimitive operationValueJson = operationJsonElem.getAsJsonPrimitive();

        if (operationValueJson == null) return null;
        if (!operationValueJson.isNumber()) return null;

        int operationId = operationJsonElem.getAsInt();

        return OperationEnum.getOperationById(operationId);
    }

    private ServerMessageBody deserializeMessageBody(
            final OperationEnum operation,
            final JsonObject messageBodyJsonObj)
    {
        if (messageBodyJsonObj == null) return null;

        if (messageBodyJsonObj.has(ServerMessageBody.C_ERROR_PROP_NAME)) {
            JsonObject errorJsonObj =
                    messageBodyJsonObj.getAsJsonObject(ServerMessageBody.C_ERROR_PROP_NAME);

            ServerMessageError serverMessageError = deserializeError(errorJsonObj);

            if (serverMessageError == null) return null;

            return ServerMessageBody.getInstance(serverMessageError);
        }

        switch (operation) {
            case SEARCHING_START: return
                    deserializeSearchingStartMessageBody(messageBodyJsonObj);
            case SEARCHING_GAME_FOUND: return
                    deserializeGameFoundMessageBody(messageBodyJsonObj);
            case CHATTING_NEW_MESSAGE: return
                    deserializeNewMessageMessageBody(messageBodyJsonObj);
            case CHATTING_STAGE_IS_OVER: return
                    deserializeChattingStageIsOverMessageBody(messageBodyJsonObj);
            case CHOOSING_USERS_CHOSEN: return
                    deserializeUsersChosenMessageBody(messageBodyJsonObj);
            case CHOOSING_STAGE_IS_OVER: return
                    deserializeChoosingStageIsOverMessageBody(messageBodyJsonObj);
        }

        return null;
    }

    private ServerMessageError deserializeError(final JsonObject errorJsonObj) {
        if (errorJsonObj == null) return null;
        if (!errorJsonObj.has(ServerMessageError.C_MESSAGE_PROP_NAME)) return null;

        JsonPrimitive errorMessageValueJson =
                errorJsonObj.getAsJsonPrimitive(ServerMessageError.C_MESSAGE_PROP_NAME);

        if (errorMessageValueJson == null) return null;

        return ServerMessageError.getInstance(errorMessageValueJson.getAsString());
    }

    private StartSearchingServerMessageBody deserializeSearchingStartMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        return StartSearchingServerMessageBody.getInstance();
    }

    private GameFoundServerMessageBody deserializeGameFoundMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        GameFoundServerMessageBodyDeserializer deserializer =
                new GameFoundServerMessageBodyDeserializer();

        return deserializer.deserialize(messageBodyJsonObj, m_curType, m_curContext);
    }

    private NewChatMessageServerMessageBody deserializeNewMessageMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        NewChatMessageServerMessageBodyDeserializer deserializer =
                new NewChatMessageServerMessageBodyDeserializer();

        return deserializer.deserialize(messageBodyJsonObj, m_curType, m_curContext);
    }

    private ChattingStageIsOverServerMessageBody deserializeChattingStageIsOverMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        return ChattingStageIsOverServerMessageBody.getInstance();
    }

    private UsersChosenServerMessageBody deserializeUsersChosenMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        return UsersChosenServerMessageBody.getInstance();
    }

    private ChoosingStageIsOverServerMessageBody deserializeChoosingStageIsOverMessageBody(
            final JsonObject messageBodyJsonObj)
    {
        ChoosingStageIsOverServerMessageBodyDeserializer deserializer =
                new ChoosingStageIsOverServerMessageBodyDeserializer();

        return deserializer.deserialize(messageBodyJsonObj, m_curType, m_curContext);
    }
}
