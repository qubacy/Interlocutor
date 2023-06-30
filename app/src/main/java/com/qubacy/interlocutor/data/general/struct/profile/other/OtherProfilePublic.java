package com.qubacy.interlocutor.data.general.struct.profile.other;

import com.qubacy.interlocutor.data.general.struct.profile.ProfilePublic;

public class OtherProfilePublic extends ProfilePublic {
    protected int m_id;

    protected OtherProfilePublic(
            final String username,
            final int id)
    {
        super(username);

        m_id = id;
    }

    public static OtherProfilePublic getInstance(
            final String username,
            final int id)
    {
        if (username == null || id < 0) return null;

        return new OtherProfilePublic(username, id);
    }

    public int getId() {
        return m_id;
    }
}
