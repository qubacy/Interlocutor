package com.qubacy.interlocutor.data.general.internal.struct.profile;

public class RemoteProfile extends RemoteProfilePublic {
    final protected String m_contact;

    protected RemoteProfile(
            final int id,
            final String username,
            final String contact)
    {
        super(id, username);

        m_contact = contact;
    }

    public static RemoteProfile getInstance(
            final int id,
            final String username,
            final String contact)
    {
        if (username == null || contact == null) return null;

        return new RemoteProfile(id, username, contact);
    }

    public String getContact() {
        return m_contact;
    }
}
