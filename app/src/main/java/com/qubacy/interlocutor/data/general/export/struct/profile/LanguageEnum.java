package com.qubacy.interlocutor.data.general.export.struct.profile;

public enum LanguageEnum {
    RU(0, "Russian"),
    EN(1, "English");

    private final int m_id;
    private final String m_name;

    private LanguageEnum(
            final int id,
            final String name)
    {
        m_id = id;
        m_name = name;
    }

    public int getId() {
        return m_id;
    }

    public String getName() {
        return m_name;
    }

    public static LanguageEnum getLanguageById(final int id) {
        if (id < 0) return null;

        for (final LanguageEnum lang : LanguageEnum.values())
            if (lang.getId() == id) return lang;

        return null;
    }
}
