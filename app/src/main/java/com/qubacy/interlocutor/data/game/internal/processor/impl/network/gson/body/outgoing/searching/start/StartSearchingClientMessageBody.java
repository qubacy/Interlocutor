package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

public class StartSearchingClientMessageBody extends ClientMessageBody {
    public static final String C_PROFILE_PROP_NAME = "profile";

    private final Profile m_profile;

    protected StartSearchingClientMessageBody(final Profile profile) {
        m_profile = profile;
    }

    public static StartSearchingClientMessageBody getInstance(
            final Profile profile)
    {
        if (profile == null) return null;

        return new StartSearchingClientMessageBody(profile);
    }

    public Profile getProfile() {
        return m_profile;
    }
}
