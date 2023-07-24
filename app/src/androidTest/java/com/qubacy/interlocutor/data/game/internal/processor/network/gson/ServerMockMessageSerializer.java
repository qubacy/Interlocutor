package com.qubacy.interlocutor.data.game.internal.processor.network.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageError;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover.ChattingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen.UsersChosenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.outgoing.chatting.newmessage.ServerMockNewMessageMessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.outgoing.choosing.stageisover.ServerMockChoosingStageIsOverMessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.outgoing.searching.gamefound.ServerMockGameFoundMessageBodySerializer;

import java.lang.reflect.Type;

public class ServerMockMessageSerializer implements JsonSerializer<Message> {
    private Type m_curType = null;
    private JsonSerializationContext m_curContext = null;

    @Override
    public JsonElement serialize(
            final Message src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        m_curType = typeOfSrc;
        m_curContext = context;

        JsonElement operationJsonElem = serializeOperation(src.getOperation());

        if (operationJsonElem == null) return null;

        JsonObject bodyJsonObj =
                serializeBody(src.getOperation(), (ServerMessageBody) src.getMessageBody());

        if (bodyJsonObj == null) return null;

        JsonObject messageJsonObj = new JsonObject();

        messageJsonObj.add(Message.C_OPERATION_PROP_NAME, operationJsonElem);
        messageJsonObj.add(Message.C_BODY_PROP_NAME, bodyJsonObj);

        return messageJsonObj;
    }

    private JsonElement serializeOperation(final OperationEnum operation) {
        if (operation == null) return null;

        JsonPrimitive operationIdValueJson = new JsonPrimitive(operation.getId());

        return operationIdValueJson;
    }

    private JsonObject serializeBody(
            final OperationEnum operation,
            final ServerMessageBody messageBody)
    {
        if (messageBody == null) return null;

        if (messageBody.getError() != null) {
            JsonObject errorJsonObj = serializeServerError(messageBody.getError());

            if (errorJsonObj == null) return null;

            JsonObject messageBodyJsonObj = new JsonObject();

            messageBodyJsonObj.add(ServerMessageBody.C_ERROR_PROP_NAME, errorJsonObj);

            return messageBodyJsonObj;
        }

        JsonElement bodyJsonElem = null;

        switch (operation) {
            case SEARCHING_START: {
                bodyJsonElem = serializeStartSearchingBody(
                        (StartSearchingServerMessageBody) messageBody);
                break;
            }
            case SEARCHING_GAME_FOUND: {
                bodyJsonElem = serializeGameFoundBody(
                        (GameFoundServerMessageBody) messageBody);
                break;
            }
            case CHATTING_NEW_MESSAGE: {
                bodyJsonElem = serializeNewMessageBody(
                    (NewChatMessageServerMessageBody) messageBody);
                break;
            }
            case CHATTING_STAGE_IS_OVER: {
                bodyJsonElem = serializeChattingStageIsOverBody(
                        (ChattingStageIsOverServerMessageBody) messageBody);
                break;
            }
            case CHOOSING_USERS_CHOSEN: {
                bodyJsonElem = serializeUsersChosenBody(
                        (UsersChosenServerMessageBody) messageBody);
                break;
            }
            case CHOOSING_STAGE_IS_OVER: {
                bodyJsonElem = serializeChoosingStageIsOverBody(
                        (ChoosingStageIsOverServerMessageBody) messageBody);
                break;
            }
        }

        if (bodyJsonElem == null) return null;

        return bodyJsonElem.getAsJsonObject();
    }

    private JsonObject serializeServerError(final ServerMessageError error) {
        JsonPrimitive messageValueJson = new JsonPrimitive(error.getMessage());
        JsonObject errorJsonObj = new JsonObject();

        errorJsonObj.add(ServerMessageError.C_MESSAGE_PROP_NAME, messageValueJson);

        return errorJsonObj;
    }

    private JsonElement serializeStartSearchingBody(
            final StartSearchingServerMessageBody messageBody)
    {
        return new JsonObject();
    }

    private JsonElement serializeGameFoundBody(
            final GameFoundServerMessageBody messageBody)
    {
        ServerMockGameFoundMessageBodySerializer serializer =
                new ServerMockGameFoundMessageBodySerializer();

        return serializer.serialize(messageBody, m_curType, m_curContext);
    }

    private JsonElement serializeNewMessageBody(
            final NewChatMessageServerMessageBody messageBody)
    {
        ServerMockNewMessageMessageBodySerializer serializer =
                new ServerMockNewMessageMessageBodySerializer();

        return serializer.serialize(messageBody, m_curType, m_curContext);
    }

    private JsonElement serializeChattingStageIsOverBody(
            final ChattingStageIsOverServerMessageBody messageBody)
    {
        return new JsonObject();
    }

    private JsonElement serializeUsersChosenBody(
            final UsersChosenServerMessageBody messageBody)
    {
        return new JsonObject();
    }

    private JsonElement serializeChoosingStageIsOverBody(
            final ChoosingStageIsOverServerMessageBody messageBody)
    {
        ServerMockChoosingStageIsOverMessageBodySerializer serializer =
                new ServerMockChoosingStageIsOverMessageBodySerializer();

        return serializer.serialize(messageBody, m_curType, m_curContext);
    }
}
