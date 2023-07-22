package com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.incoming.choosing.userschosen;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBody;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServerMockUsersChosenMessageBodyDeserializer
        extends MessageBodyDeserializer<UsersChosenClientMessageBody>
{
    @Override
    public UsersChosenClientMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        JsonObject messageBodyJsonObj = json.getAsJsonObject();

        if (messageBodyJsonObj == null) return null;

        List<Integer> chosenUserIdList =
                deserializeChosenUserIdList(
                        messageBodyJsonObj.getAsJsonArray(
                                UsersChosenClientMessageBody.C_USER_ID_LIST_PROP_NAME));

        if (chosenUserIdList == null) return null;

        return UsersChosenClientMessageBody.getInstance(chosenUserIdList);
    }

    private List<Integer> deserializeChosenUserIdList(
            final JsonArray chosenUserIdListJsonArray)
    {
        if (chosenUserIdListJsonArray == null) return null;

        List<Integer> chosenUserIdList = new ArrayList<>();

        for (final JsonElement chosenUserIdJsonElem : chosenUserIdListJsonArray) {
            if (chosenUserIdJsonElem == null) return null;

            JsonPrimitive chosenUserIdValueJson = chosenUserIdJsonElem.getAsJsonPrimitive();

            if (chosenUserIdValueJson == null) return null;

            int chosenUserId = chosenUserIdValueJson.getAsInt();

            chosenUserIdList.add(chosenUserId);
        }

        return chosenUserIdList;
    }
}
