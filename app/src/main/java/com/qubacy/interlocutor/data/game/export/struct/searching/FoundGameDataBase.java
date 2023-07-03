package com.qubacy.interlocutor.data.game.export.struct.searching;

import java.io.Serializable;

public abstract class FoundGameDataBase implements Serializable {
    protected final int m_localProfileId;

    protected FoundGameDataBase(final int localProfileId) {
        m_localProfileId = localProfileId;
    }

    public int getLocalProfileId() {
        return m_localProfileId;
    }

}
