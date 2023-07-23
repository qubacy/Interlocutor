package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state;

public abstract class ServerMockRoomState {
    protected final long m_startTime;
    protected final long m_duration;

    protected ServerMockRoomState(
            final long startTime,
            final long duration)
    {
        m_startTime = startTime;
        m_duration = duration;
    }

    public abstract ServerMockRoomStateTypeEnum getType();

    public long getStartTime() {
        return m_startTime;
    }

    public long getDuration() {
        return m_duration;
    }

    public boolean isOver(final long curTime) {
        return (curTime >= m_startTime + m_duration);
    }
}
