package com.qubacy.interlocutor.data.general.struct.profile.other;

public class OtherProfile extends OtherProfilePublic {
    final protected String m_contact;

    protected OtherProfile(
            final String username,
            final int id,
            final String contact)
    {
        super(username, id);

        m_contact = contact;
    }

    public static OtherProfile getInstance(
            final String username,
            final int id,
            final String contact)
    {
        if (username == null || contact == null) return null;

        return new OtherProfile(username, id, contact);
    }

    public String getContact() {
        return m_contact;
    }
}
