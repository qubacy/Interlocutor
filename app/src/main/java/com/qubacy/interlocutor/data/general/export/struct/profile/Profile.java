package com.qubacy.interlocutor.data.general.export.struct.profile;

import java.io.Serializable;

public class Profile extends ProfilePublic implements Serializable {
    public static final String C_USERNAME_PROP_NAME = "username";
    public static final String C_CONTACT_PROP_NAME = "contact";

    final protected String m_contact;

    protected Profile(
            final int id,
            final String username,
            final String contact)
    {
        super(id, username);

        m_contact = contact;
    }

    public static Profile getInstance(
            final String username,
            final String contact)
    {
        if (username == null || contact == null) return null;

        return new Profile(C_DEFAULT_LOCAL_ID, username, contact);
    }

    public static Profile getInstance(
            final int id,
            final String username,
            final String contact)
    {
        if (id < 0 || username == null || contact == null) return null;

        return new Profile(id, username, contact);
    }

    public String getContact() {
        return m_contact;
    }
}
