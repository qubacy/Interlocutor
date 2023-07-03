package com.qubacy.interlocutor.ui.screen.play.chatting.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.data.game.export.service.broadcast.GameServiceBroadcastReceiver;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;

public class PlayChattingFragmentViewModel extends ViewModel {


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
