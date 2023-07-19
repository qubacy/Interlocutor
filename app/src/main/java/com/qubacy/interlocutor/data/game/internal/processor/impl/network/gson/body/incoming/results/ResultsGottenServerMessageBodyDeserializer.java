package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.results;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultsGottenServerMessageBodyDeserializer
    extends MessageBodyDeserializer<ResultsGottenServerMessageBody>
{

    @Override
    public ResultsGottenServerMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        JsonObject resultsMessageBodyJsonObj = json.getAsJsonObject();

        if (resultsMessageBodyJsonObj == null) return null;

        JsonArray matchedUserDataListJsonArray =
                resultsMessageBodyJsonObj.getAsJsonArray(
                        ResultsGottenServerMessageBody.C_MATCHED_USERS_PROP_NAME);

        if (matchedUserDataListJsonArray == null) return null;

        List<MatchedUserProfileData> matchedUserDataList =
                deserializeMatchedUserDataList(matchedUserDataListJsonArray);

        if (matchedUserDataList == null) return null;

        return ResultsGottenServerMessageBody.getInstance(matchedUserDataList);
    }

    private List<MatchedUserProfileData> deserializeMatchedUserDataList(
            final JsonArray matchedUserDataListJsonArray)
    {
        List<MatchedUserProfileData> matchedUserProfileDataList =
                new ArrayList<>();

        for (final JsonElement matchedUserDataJsonElem : matchedUserDataListJsonArray) {
            if (matchedUserDataJsonElem == null) return null;

            JsonObject matchedUserDataJsonObj =
                    matchedUserDataJsonElem.getAsJsonObject();

            if (matchedUserDataJsonObj == null) return null;

            MatchedUserProfileData matchedUserProfileData =
                    deserializeMatchedUserData(matchedUserDataJsonObj);

            if (matchedUserProfileData == null) return null;

            matchedUserProfileDataList.add(matchedUserProfileData);
        }

        return matchedUserProfileDataList;
    }

    private MatchedUserProfileData deserializeMatchedUserData(
            final JsonObject matchedUserData)
    {
        JsonPrimitive idValueJson =
                matchedUserData.getAsJsonPrimitive(MatchedUserProfileData.C_ID_PROP_NAME);
        JsonPrimitive contactValueJson =
                matchedUserData.getAsJsonPrimitive(MatchedUserProfileData.C_CONTACT_PROP_NAME);

        if (idValueJson == null || contactValueJson == null)
            return null;

        int id = idValueJson.getAsInt();
        String contact = contactValueJson.getAsString();

        if (contact == null) return null;

        return MatchedUserProfileData.getInstance(id, contact);
    }
}
