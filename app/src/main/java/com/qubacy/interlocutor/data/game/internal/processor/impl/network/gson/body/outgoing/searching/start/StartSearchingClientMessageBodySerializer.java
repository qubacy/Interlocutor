package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodySerializer;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

import java.lang.reflect.Type;

public class StartSearchingClientMessageBodySerializer
        extends MessageBodySerializer<StartSearchingClientMessageBody>
{

    @Override
    public JsonElement serialize(
            final StartSearchingClientMessageBody src,
            final Type typeOfSrc,
            final JsonSerializationContext context)
    {
        if (src == null) return null;

        JsonObject profileJsonObj = serializeProfile(src.getProfile());

        if (profileJsonObj == null) return null;

        JsonObject startSearchingBodyJsonObj = new JsonObject();

        startSearchingBodyJsonObj.add(
                StartSearchingClientMessageBody.C_PROFILE_PROP_NAME, profileJsonObj);

        return startSearchingBodyJsonObj;
    }

    private JsonObject serializeProfile(final Profile profile) {
        if (profile == null) return null;

        JsonObject profileJsonObj = new JsonObject();

        JsonPrimitive usernameValueJson = new JsonPrimitive(profile.getUsername());
        JsonPrimitive contactValueJson = new JsonPrimitive(profile.getContact());
        JsonPrimitive langIdValueJson = new JsonPrimitive(profile.getLang().getId());

        profileJsonObj.add(Profile.C_USERNAME_PROP_NAME, usernameValueJson);
        profileJsonObj.add(Profile.C_CONTACT_PROP_NAME, contactValueJson);
        profileJsonObj.add(Profile.C_LANG_PROP_NAME, langIdValueJson);

        return profileJsonObj;
    }
}
