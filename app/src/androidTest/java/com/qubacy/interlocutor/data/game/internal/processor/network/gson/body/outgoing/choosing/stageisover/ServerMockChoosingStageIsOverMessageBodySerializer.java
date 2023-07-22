package com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.outgoing.choosing.stageisover;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;

import java.lang.reflect.Type;
import java.util.List;

public class ServerMockChoosingStageIsOverMessageBodySerializer
    extends MessageBodySerializer<ChoosingStageIsOverServerMessageBody>
{

    @Override
    public JsonElement serialize(
            final ChoosingStageIsOverServerMessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        JsonArray matchedUserDataJsonArray =
                serializeMatchedUserDataList(src.getMatchedUsers());

        if (matchedUserDataJsonArray == null) return null;

        JsonObject messageBodyJsonObj = new JsonObject();

        messageBodyJsonObj.add(
                ChoosingStageIsOverServerMessageBody.C_MATCHED_USERS_PROP_NAME,
                matchedUserDataJsonArray);

        return messageBodyJsonObj;
    }

    private JsonArray serializeMatchedUserDataList(
            final List<MatchedUserProfileData> matchedUserProfileDataList)
    {
        if (matchedUserProfileDataList == null) return null;

        JsonArray matchedUserDataJsonArray = new JsonArray();

        for (final MatchedUserProfileData matchedUserProfileData : matchedUserProfileDataList) {
            if (matchedUserProfileData == null) return null;

            JsonPrimitive userIdValueJson =
                    new JsonPrimitive(matchedUserProfileData.getId());
            JsonPrimitive contactValueJson =
                    new JsonPrimitive(matchedUserProfileData.getContact());
            JsonObject matchedUserDataJsonObj = new JsonObject();

            matchedUserDataJsonObj.add(MatchedUserProfileData.C_ID_PROP_NAME, userIdValueJson);
            matchedUserDataJsonObj.add(MatchedUserProfileData.C_CONTACT_PROP_NAME, contactValueJson);

            matchedUserDataJsonArray.add(matchedUserDataJsonObj);
        }

        return matchedUserDataJsonArray;
    }
}
