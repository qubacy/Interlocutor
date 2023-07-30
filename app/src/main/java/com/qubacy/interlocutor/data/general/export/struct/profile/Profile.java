package com.qubacy.interlocutor.data.general.export.struct.profile;

import java.io.Serializable;

public class Profile extends ProfilePublic implements Serializable {
    public static final String C_USERNAME_PROP_NAME = "username";
    public static final String C_CONTACT_PROP_NAME = "contact";
    public static final String C_LANG_PROP_NAME = "lang";

    final protected String m_contact;
    final protected LanguageEnum m_lang;

    protected Profile(
            final int id,
            final String username,
            final String contact,
            final LanguageEnum lang)
    {
        super(id, username);

        m_contact = contact;
        m_lang = lang;
    }

    public static Profile getInstance(
            final String username,
            final String contact,
            final LanguageEnum lang)
    {
        if (username == null || contact == null || lang == null)
            return null;

        return new Profile(C_DEFAULT_LOCAL_ID, username, contact, lang);
    }

    public static Profile getInstance(
            final int id,
            final String username,
            final String contact,
            final LanguageEnum lang)
    {
        if (id < 0 || username == null || contact == null || lang == null)
            return null;

        return new Profile(id, username, contact, lang);
    }

    public String getContact() {
        return m_contact;
    }

    public LanguageEnum getLang() {
        return m_lang;
    }
}
