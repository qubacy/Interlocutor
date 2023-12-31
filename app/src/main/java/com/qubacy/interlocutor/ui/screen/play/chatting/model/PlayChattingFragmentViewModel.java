package com.qubacy.interlocutor.ui.screen.play.chatting.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.data.game.export.service.broadcast.GameServiceBroadcastReceiver;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.ui.screen.play.common.task.TextViewTimerAsyncTaskCallback;

import java.util.LinkedList;
import java.util.List;

public class PlayChattingFragmentViewModel extends ViewModel
    implements TextViewTimerAsyncTaskCallback
{
    protected Long m_chattingTimeRemaining = null;
    protected final List<Message> m_messageList;

    public PlayChattingFragmentViewModel() {
        m_messageList = new LinkedList<>();
    }

    public Long getRemainingTime() {
        return m_chattingTimeRemaining;
    }

    public Message getMessageByIndex(final int index) {
        if  (index < 0 || index >= m_messageList.size())
            return null;

        return m_messageList.get(index);
    }

    public int getMessageCount() {
        return m_messageList.size();
    }

    public boolean setRemainingTime(@NonNull final Long newRemainingTime) {
        if (newRemainingTime < 0) return false;

        m_chattingTimeRemaining = newRemainingTime;

        return true;
    }

    public void addMessage(@NonNull final Message message) {
        m_messageList.add(message);
    }

    public boolean onMessageSendingRequested(
            @NonNull final String messageText,
            @NonNull final Context context)
    {
        Message message = Message.getInstance(messageText);

        if (message == null) return false;

        GameServiceBroadcastReceiver.broadcastSendMessage(context, message);

        return true;
    }
}
