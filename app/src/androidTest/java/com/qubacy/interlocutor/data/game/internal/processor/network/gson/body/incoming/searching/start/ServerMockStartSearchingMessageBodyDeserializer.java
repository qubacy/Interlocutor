package com.qubacy.interlocutor.data.game.internal.processor.network.gson.body.incoming.searching.start;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.MessageBodyDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.general.export.struct.profile.LanguageEnum;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

import java.lang.reflect.Type;

public class ServerMockStartSearchingMessageBodyDeserializer
        extends MessageBodyDeserializer<StartSearchingClientMessageBody>
{

    @Override
    public StartSearchingClientMessageBody deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null) return null;

        JsonObject messageBodyJsonObj = json.getAsJsonObject();

        if (messageBodyJsonObj == null) return null;

        Profile profile =
                deserializeProfile(
                        messageBodyJsonObj.getAsJsonObject(
                                StartSearchingClientMessageBody.C_PROFILE_PROP_NAME));

        if (profile == null) return null;

        return StartSearchingClientMessageBody.getInstance(profile);
    }

    private Profile deserializeProfile(final JsonObject profileJsonObj) {
        if (profileJsonObj == null) return null;

        JsonPrimitive usernameValueJson =
                profileJsonObj.getAsJsonPrimitive(Profile.C_USERNAME_PROP_NAME);
        JsonPrimitive contactValueJson =
                profileJsonObj.getAsJsonPrimitive(Profile.C_CONTACT_PROP_NAME);
        JsonPrimitive langIdValueJson =
                profileJsonObj.getAsJsonPrimitive(Profile.C_LANG_PROP_NAME);

        if (usernameValueJson == null || contactValueJson == null || langIdValueJson == null)
            return null;

        String username = usernameValueJson.getAsString();
        String contact = contactValueJson.getAsString();
        LanguageEnum lang = LanguageEnum.getLanguageById(langIdValueJson.getAsInt());

        if (username == null || contact == null || lang == null) return null;

        return Profile.getInstance(username, contact, lang);
    }
}
