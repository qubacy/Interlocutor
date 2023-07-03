package com.qubacy.interlocutor.ui.screen.play.chatting.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;

public interface PlayChattingFragmentBroadcastReceiverCallback {
    public void onMessageReceived(@NonNull final Message message);
    public void onTimeIsOver();
}
