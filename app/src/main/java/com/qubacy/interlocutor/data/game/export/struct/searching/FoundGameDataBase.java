package com.qubacy.interlocutor.data.game.export.struct.searching;

import java.io.Serializable;

public abstract class FoundGameDataBase implements Serializable {
    protected final int m_localProfileId;

    protected final long m_startSessionTime;

    protected final long m_chattingStageDuration;
    protected final long m_choosingStageDuration;

    protected FoundGameDataBase(
            final int localProfileId,
            final long startSessionTime,
            final long chattingStageDuration,
            final long choosingStageDuration)
    {
        m_localProfileId = localProfileId;

        m_startSessionTime = startSessionTime;

        m_chattingStageDuration = chattingStageDuration;
        m_choosingStageDuration = choosingStageDuration;
    }

    public int getLocalProfileId() {
        return m_localProfileId;
    }

    public long getStartSessionTime() {
        return m_startSessionTime;
    }

    public long getChattingStageDuration() {
        return m_chattingStageDuration;
    }

    public long getChoosingStageDuration() {
        return m_choosingStageDuration;
    }
}
