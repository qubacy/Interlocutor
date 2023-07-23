package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.user;

import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;

public class ServerMockUser {
    private final RemoteProfile m_profile;

    protected ServerMockUser(final RemoteProfile profile) {
        m_profile = profile;
    }

    public static ServerMockUser getInstance(final RemoteProfile profile) {
        if (profile == null) return null;

        return new ServerMockUser(profile);
    }

    public RemoteProfile getProfile() {
        return m_profile;
    }
}
