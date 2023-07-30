package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.user;

import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

public class ServerMockUser {
    private final Profile m_profile;

    protected ServerMockUser(final Profile profile) {
        m_profile = profile;
    }

    public static ServerMockUser getInstance(final Profile profile) {
        if (profile == null) return null;

        return new ServerMockUser(profile);
    }

    public Profile getProfile() {
        return m_profile;
    }
}
