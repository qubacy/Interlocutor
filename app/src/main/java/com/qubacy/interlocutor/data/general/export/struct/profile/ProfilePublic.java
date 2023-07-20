package com.qubacy.interlocutor.data.general.export.struct.profile;

import java.io.Serializable;
import java.util.Objects;

public class ProfilePublic implements Serializable {
    public static final int C_DEFAULT_LOCAL_ID = 0;

    final protected int m_id;
    final protected String m_username;

    protected ProfilePublic(
            final int id,
            final String username)
    {
        m_id = id;
        m_username = username;
    }

    public static ProfilePublic getInstance(
            final int id,
            final String username)
    {
        if (id < 0 || username == null) return null;

        return new ProfilePublic(id, username);
    }

    public static ProfilePublic getInstance(
            final String username)
    {
        if (username == null) return null;

        return new ProfilePublic(C_DEFAULT_LOCAL_ID, username);
    }

    public int getId() {
        return m_id;
    }

    public String getUsername() {
        return m_username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePublic that = (ProfilePublic) o;

        return m_id == that.m_id &&
                Objects.equals(m_username, that.m_username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_id, m_username);
    }
}
