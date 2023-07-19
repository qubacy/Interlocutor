package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;

import java.lang.reflect.Type;
import java.util.List;

public class UsersChosenClientMessageBodySerializer
        extends MessageBodySerializer<UsersChosenClientMessageBody>
{

    @Override
    public JsonElement serialize(
            final UsersChosenClientMessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        JsonArray serializedChosenUserIdListJsonArray =
                serializeChosenUserIdList(src.chosenUserIdList);

        if (serializedChosenUserIdListJsonArray == null)
            return null;

        JsonObject usersChosenBodyJsonObj = new JsonObject();

        usersChosenBodyJsonObj.add(
                UsersChosenClientMessageBody.C_USER_ID_LIST_PROP_NAME,
                serializedChosenUserIdListJsonArray);

        return null;
    }

    private JsonArray serializeChosenUserIdList(final List<Integer> chosenUserIdList) {
        if (chosenUserIdList == null) return null;

        JsonArray userIdListJsonArray = new JsonArray();

        for (final Integer userId : chosenUserIdList) {
            if (userId == null) return null;

            userIdListJsonArray.add(userId);
        }

        return userIdListJsonArray;
    }
}
