package com.qubacy.interlocutor.data.general.struct.profile;

import java.io.Serializable;

public class ProfilePublic implements Serializable {
    final protected String m_username;

    protected ProfilePublic(
            final String username)
    {
        m_username = username;
    }

    public static ProfilePublic getInstance(
            final String username)
    {
        if (username == null) return null;

        return new ProfilePublic(username);
    }

    public String getUsername() {
        return m_username;
    }
}
