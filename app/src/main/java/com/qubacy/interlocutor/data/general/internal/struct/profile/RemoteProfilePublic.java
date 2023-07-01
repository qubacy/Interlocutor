package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public class RemoteProfilePublic extends ProfilePublic {
    protected RemoteProfilePublic(
            final int id,
            final String username)
    {
        super(id, username);
    }

    public static RemoteProfilePublic getInstance(
            final int id,
            final String username)
    {
        if (id < 0 || username == null) return null;

        return new RemoteProfilePublic(id, username);
    }

    public int getId() {
        return m_id;
    }
}
