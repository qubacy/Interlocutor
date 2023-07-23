package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state;

public class ServerMockRoomSearchingState extends ServerMockRoomState {
    public static final long C_DEFAULT_DURATION = 2000;

    protected ServerMockRoomSearchingState(
            final long startTime,
            final long duration)
    {
        super(startTime, duration);
    }

    public static ServerMockRoomSearchingState getInstance(
            final long startTime,
            final long duration)
    {
        if (startTime <= 0 || duration <= 0) return null;

        return new ServerMockRoomSearchingState(startTime, duration);
    }

    public static ServerMockRoomSearchingState getInstance(
            final long startTime)
    {
        if (startTime <= 0) return null;

        return new ServerMockRoomSearchingState(startTime, C_DEFAULT_DURATION);
    }

    @Override
    public ServerMockRoomStateTypeEnum getType() {
        return ServerMockRoomStateTypeEnum.SEARCHING;
    }
}
