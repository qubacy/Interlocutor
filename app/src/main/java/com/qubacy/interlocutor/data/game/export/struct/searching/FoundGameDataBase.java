package com.qubacy.interlocutor.data.game.export.struct.searching;

import java.io.Serializable;
import java.util.Objects;

public abstract class FoundGameDataBase implements Serializable {
    protected final int m_localProfileId;

    protected final long m_startSessionTime;

    protected final long m_chattingStageDuration;
    protected final long m_choosingStageDuration;

    protected final String m_chattingTopic;

    protected FoundGameDataBase(
            final int localProfileId,
            final long startSessionTime,
            final long chattingStageDuration,
            final long choosingStageDuration,
            final String chattingTopic)
    {
        m_localProfileId = localProfileId;

        m_startSessionTime = startSessionTime;

        m_chattingStageDuration = chattingStageDuration;
        m_choosingStageDuration = choosingStageDuration;

        m_chattingTopic = chattingTopic;
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

    public String getChattingTopic() {
        return m_chattingTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoundGameDataBase that = (FoundGameDataBase) o;

        return m_localProfileId == that.m_localProfileId &&
                m_startSessionTime == that.m_startSessionTime &&
                m_chattingStageDuration == that.m_chattingStageDuration &&
                m_choosingStageDuration == that.m_choosingStageDuration &&
                Objects.equals(m_chattingTopic, that.m_chattingTopic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                m_localProfileId,
                m_startSessionTime,
                m_chattingStageDuration,
                m_choosingStageDuration,
                m_chattingTopic);
    }
}
