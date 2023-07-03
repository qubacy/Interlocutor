package com.qubacy.interlocutor.ui.screen.play.chatting.model;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;

public interface PlayChattingViewModel {
    public Message getMessageByIndex(final int index);
    public int getMessageCount();
    public void addMessage(@NonNull final Message message);
}
