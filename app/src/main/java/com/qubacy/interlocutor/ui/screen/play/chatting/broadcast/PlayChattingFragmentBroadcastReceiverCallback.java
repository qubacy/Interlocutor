package com.qubacy.interlocutor.ui.screen.play.chatting.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.struct.message.RemoteMessage;

public interface PlayChattingFragmentBroadcastReceiverCallback {
    public void onMessageReceived(@NonNull final RemoteMessage message);
}
