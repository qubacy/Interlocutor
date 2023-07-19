package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameFoundServerMessageBodyDeserializer
        extends MessageBodyDeserializer<GameFoundServerMessageBody>
{

    @Override
    public GameFoundServerMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        JsonObject gameFoundJsonObj = json.getAsJsonObject();

        if (gameFoundJsonObj == null) return null;

        RemoteFoundGameData remoteFoundGameData =
                deserializeFoundGameData(gameFoundJsonObj);

        return GameFoundServerMessageBody.getInstance(remoteFoundGameData);
    }

    private RemoteFoundGameData deserializeFoundGameData(
            final JsonObject gameFoundJsonObj)
    {
        JsonPrimitive localProfileIdValueJson =
                gameFoundJsonObj.getAsJsonPrimitive(RemoteFoundGameData.C_LOCAL_PROFILE_ID_PROP_NAME);
        JsonPrimitive startSessionTimeValueJson =
                gameFoundJsonObj.getAsJsonPrimitive(RemoteFoundGameData.C_START_SESSION_TIME_PROP_NAME);
        JsonPrimitive chattingStageDurationValueJson =
                gameFoundJsonObj.getAsJsonPrimitive(RemoteFoundGameData.C_CHATTING_STAGE_DURATION_PROP_NAME);
        JsonPrimitive choosingStageDurationValueJson =
                gameFoundJsonObj.getAsJsonPrimitive(RemoteFoundGameData.C_CHOOSING_STAGE_DURATION_PROP_NAME);
        JsonPrimitive chattingTopicValueJson =
                gameFoundJsonObj.getAsJsonPrimitive(RemoteFoundGameData.C_CHATTING_TOPIC_PROP_NAME);
        JsonArray profilePublicListJsonArray =
                gameFoundJsonObj.getAsJsonArray(RemoteFoundGameData.C_PROFILE_PUBLIC_LIST);

        if (localProfileIdValueJson == null || startSessionTimeValueJson == null ||
            chattingStageDurationValueJson == null || choosingStageDurationValueJson == null ||
            chattingTopicValueJson == null || profilePublicListJsonArray == null)
        {
            return null;
        }

        int localProfileId = localProfileIdValueJson.getAsInt();
        long startSessionTime = localProfileIdValueJson.getAsLong();
        long chattingStageDuration = chattingStageDurationValueJson.getAsLong();
        long choosingStageDuration = choosingStageDurationValueJson.getAsLong();
        String chattingTopic = chattingTopicValueJson.getAsString();
        List<RemoteProfilePublic> profilePublicList =
                deserializeProfilePublicList(profilePublicListJsonArray);

        if (chattingTopic == null || profilePublicList == null)
            return null;

        return RemoteFoundGameData.getInstance(
                localProfileId,
                startSessionTime,
                chattingStageDuration,
                choosingStageDuration,
                chattingTopic,
                profilePublicList);
    }

    private List<RemoteProfilePublic> deserializeProfilePublicList(
            final JsonArray profilePublicListJsonArray)
    {
        List<RemoteProfilePublic> profilePublicList = new ArrayList<>();

        for (final JsonElement profilePublicJsonElem : profilePublicListJsonArray) {
            if (profilePublicJsonElem == null) return null;

            JsonObject profilePublicJsonObj = profilePublicJsonElem.getAsJsonObject();

            if (profilePublicJsonObj == null) return null;

            RemoteProfilePublic profilePublic =
                    deserializeProfilePublic(profilePublicJsonObj);

            if (profilePublic == null) return null;

            profilePublicList.add(profilePublic);
        }

        return profilePublicList;
    }

    private RemoteProfilePublic deserializeProfilePublic(
            final JsonObject profilePublicJsonObj)
    {
        JsonPrimitive idValueJson =
                profilePublicJsonObj.getAsJsonPrimitive(RemoteProfilePublic.C_ID_PROP_NAME);
        JsonPrimitive usernameValueJson =
                profilePublicJsonObj.getAsJsonPrimitive(RemoteProfilePublic.C_USERNAME_PROP_NAME);

        if (idValueJson == null || usernameValueJson == null)
            return null;

        int id = idValueJson.getAsInt();
        String username = usernameValueJson.getAsString();

        if (username == null) return null;

        return RemoteProfilePublic.getInstance(id, username);
    }
}
