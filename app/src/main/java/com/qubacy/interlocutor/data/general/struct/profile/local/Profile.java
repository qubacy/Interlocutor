package com.qubacy.interlocutor.data.general.struct.profile.local;

import com.qubacy.interlocutor.data.general.struct.profile.ProfilePublic;

import java.io.Serializable;

public class Profile extends ProfilePublic implements Serializable {
    final protected String m_contact;

    protected Profile(
            final String username,
            final String contact)
    {
        super(username);

        m_contact = contact;
    }

    public static Profile getInstance(
            final String username,
            final String contact)
    {
        if (username == null || contact == null) return null;

        return new Profile(username, contact);
    }

    public String getContact() {
        return m_contact;
    }
}
