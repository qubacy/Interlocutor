package com.qubacy.interlocutor.data.game.internal.processor.network.websocket.data.room.state;

public class ServerMockRoomChattingState extends ServerMockRoomState {
    public static final long C_DEFAULT_DURATION = 3000;

//    private final List<RemoteMessage> m_messageList;

    protected ServerMockRoomChattingState(
            final long startTime,
            final long duration
    ) {
        super(startTime, duration);

//        m_messageList = new ArrayList<>();
    }

    public static ServerMockRoomChattingState getInstance(
            final long startTime)
    {
        if (startTime <= 0) return null;

        return new ServerMockRoomChattingState(startTime, C_DEFAULT_DURATION);
    }

    @Override
    public ServerMockRoomStateTypeEnum getType() {
        return ServerMockRoomStateTypeEnum.CHATTING;
    }

//    public boolean addMessage(final RemoteMessage message) {
//        if (message == null) return false;
//
//        m_messageList.add(message);
//
//        return true;
//    }
}
