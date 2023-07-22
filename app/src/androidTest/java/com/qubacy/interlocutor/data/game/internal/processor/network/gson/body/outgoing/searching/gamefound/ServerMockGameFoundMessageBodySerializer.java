package com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.outgoing.searching.gamefound;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.lang.reflect.Type;

public class ServerMockGameFoundMessageBodySerializer
    extends MessageBodySerializer<GameFoundServerMessageBody>
{

    @Override
    public JsonElement serialize(
            final GameFoundServerMessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        JsonObject foundGameDataJsonObj = serializeFoundGameData(src.getFoundGameData());

        if (foundGameDataJsonObj == null) return null;

        JsonObject messageBodyJsonObj = new JsonObject();

        messageBodyJsonObj.add(
                GameFoundServerMessageBody.C_FOUND_GAME_DATA_PROP_NAME, foundGameDataJsonObj);

        return messageBodyJsonObj;
    }

    private JsonObject serializeFoundGameData(final RemoteFoundGameData foundGameData) {
        if (foundGameData == null) return null;

        JsonPrimitive localUserIdValueJson =
                new JsonPrimitive(foundGameData.getLocalProfileId());
        JsonPrimitive startSessionTimeValueJson =
                new JsonPrimitive(foundGameData.getStartSessionTime());
        JsonPrimitive chattingStageDurationValueJson =
                new JsonPrimitive(foundGameData.getChattingStageDuration());
        JsonPrimitive choosingStageDurationValueJson =
                new JsonPrimitive(foundGameData.getChoosingStageDuration());
        JsonPrimitive chattingTopicValueJson =
                new JsonPrimitive(foundGameData.getChattingTopic());
        JsonArray userProfileDataJsonArray = new JsonArray();

        for (final RemoteProfilePublic userProfileData : foundGameData.getProfilePublicList()) {
            if (userProfileData == null) return null;

            JsonPrimitive userIdValueJson = new JsonPrimitive(userProfileData.getId());
            JsonPrimitive usernameValueJson = new JsonPrimitive(userProfileData.getUsername());
            JsonObject userProfileDataJsonObj = new JsonObject();

            userProfileDataJsonObj.add(RemoteProfilePublic.C_ID_PROP_NAME, userIdValueJson);
            userProfileDataJsonObj.add(RemoteProfilePublic.C_USERNAME_PROP_NAME, usernameValueJson);

            userProfileDataJsonArray.add(userProfileDataJsonObj);
        }

        JsonObject foundGameDataJsonObj = new JsonObject();

        foundGameDataJsonObj.add(
                RemoteFoundGameData.C_LOCAL_PROFILE_ID_PROP_NAME, localUserIdValueJson);
        foundGameDataJsonObj.add(
                RemoteFoundGameData.C_START_SESSION_TIME_PROP_NAME, startSessionTimeValueJson);
        foundGameDataJsonObj.add(
                RemoteFoundGameData.C_CHATTING_STAGE_DURATION_PROP_NAME, chattingStageDurationValueJson);
        foundGameDataJsonObj.add(
                RemoteFoundGameData.C_CHOOSING_STAGE_DURATION_PROP_NAME, choosingStageDurationValueJson);
        foundGameDataJsonObj.add(
                RemoteFoundGameData.C_CHATTING_TOPIC_PROP_NAME, chattingTopicValueJson);
        foundGameDataJsonObj.add(
                RemoteFoundGameData.C_PROFILE_PUBLIC_LIST, userProfileDataJsonArray);

        return foundGameDataJsonObj;
    }
}
