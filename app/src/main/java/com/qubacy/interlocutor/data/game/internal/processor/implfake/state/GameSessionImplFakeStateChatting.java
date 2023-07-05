package com.qubacy.interlocutor.data.game.internal.processor.implfake.state;

import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChatting;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;

public class GameSessionImplFakeStateChatting extends GameSessionStateChatting
    implements Serializable
{
    private static final long C_CHATTING_NEW_MESSAGE_DELAY_MILLISECONDS = 5000;

    private final Queue<RemoteMessage> m_fakePendingMessageQueue;

    private long m_lastMessagePolledTime = 0;

    protected GameSessionImplFakeStateChatting() {
        super();

        m_fakePendingMessageQueue = new ArrayDeque<RemoteMessage>() {
            {
                add(RemoteMessage.getInstance(0, "hi there"));
                add(RemoteMessage.getInstance(1, "hi"));
            }
        };
    }

    public static GameSessionImplFakeStateChatting getInstance() {
        return new GameSessionImplFakeStateChatting();
    }

    public RemoteMessage takeFakePendingMessage() {
        long curTime = System.currentTimeMillis();

        if (m_lastMessagePolledTime != 0 &&
            (m_lastMessagePolledTime + C_CHATTING_NEW_MESSAGE_DELAY_MILLISECONDS > curTime))
        {
            return null;
        }

        m_lastMessagePolledTime = curTime;

        return m_fakePendingMessageQueue.poll();
    }

    public void addFakePendingMessage(final RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;

        m_fakePendingMessageQueue.add(remoteMessage);
    }
}
