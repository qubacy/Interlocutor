package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.stop.StopSearchingClientMessageBody;

import java.lang.reflect.Type;

public class MessageSerializer implements JsonSerializer<Message> {

    private Type m_curTypeOfSrc;
    private JsonSerializationContext m_curContext;

    @Override
    public JsonElement serialize(
            final Message src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        m_curTypeOfSrc = typeOfSrc;
        m_curContext = context;

        JsonObject rootJsonObj = new JsonObject();
        JsonElement operationJsonElem = new JsonPrimitive(src.getOperation().getId());

        rootJsonObj.add(Message.C_OPERATION_PROP_NAME, operationJsonElem);

        if (!serializeBody(src.getOperation(), src.getMessageBody(), rootJsonObj))
            return null;

        return rootJsonObj;
    }

    private boolean serializeBody(
            final OperationEnum operationEnum,
            final MessageBody messageBody,
            final JsonObject rootJsonObj)
    {
        if (messageBody == null) return false;

        JsonObject bodyJsonObj = null;

        switch (operationEnum) {
            case SEARCHING_START: {
                bodyJsonObj =
                        serializeSearchingStartBody(
                                (StartSearchingClientMessageBody) messageBody);
                break;
            }
            case SEARCHING_STOP: {
                bodyJsonObj =
                        serializeSearchingStopBody(
                                (StopSearchingClientMessageBody) messageBody);
                break;
            }
            case CHATTING_NEW_MESSAGE: {
                bodyJsonObj =
                        serializeChattingNewMessageBody(
                                (NewMessageClientMessageBody) messageBody);
                break;
            }
            case CHOOSING_USERS_CHOSEN: {
                bodyJsonObj =
                        serializeChoosingUsersChosenBody(
                                (UsersChosenClientMessageBody) messageBody);
                break;
            }
        }

        if (bodyJsonObj == null) return false;

        rootJsonObj.add(Message.C_BODY_PROP_NAME, bodyJsonObj);

        return true;
    }

    private JsonObject serializeSearchingStartBody(
            final StartSearchingClientMessageBody messageBody)
    {
        if (messageBody == null) return null;

        return new JsonObject();
    }

    private JsonObject serializeSearchingStopBody(
            final StopSearchingClientMessageBody messageBody)
    {
        if (messageBody == null) return null;

        return new JsonObject();
    }

    private JsonObject serializeChattingNewMessageBody(
            final NewMessageClientMessageBody messageBody)
    {
        if (messageBody == null) return null;

        NewMessageClientMessageBodySerializer serializer =
                new NewMessageClientMessageBodySerializer();

        JsonElement serializedMessageBodyJsonElem =
                serializer.serialize(messageBody, m_curTypeOfSrc, m_curContext);

        if (serializedMessageBodyJsonElem == null) return null;

        return serializedMessageBodyJsonElem.getAsJsonObject();
    }

    private JsonObject serializeChoosingUsersChosenBody(
            final UsersChosenClientMessageBody messageBody)
    {
        if (messageBody == null) return null;

        UsersChosenClientMessageBodySerializer serializer =
                new UsersChosenClientMessageBodySerializer();

        JsonElement serializedMessageBodyJsonElem =
                serializer.serialize(messageBody, m_curTypeOfSrc, m_curContext);

        if (serializedMessageBodyJsonElem == null) return null;

        return serializedMessageBodyJsonElem.getAsJsonObject();
    }
}
