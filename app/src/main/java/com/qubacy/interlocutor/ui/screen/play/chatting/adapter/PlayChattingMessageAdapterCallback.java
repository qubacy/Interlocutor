package com.qubacy.interlocutor.ui.screen.play.chatting.adapter;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public interface PlayChattingMessageAdapterCallback {
    public Message getMessageByIndex(final int index);
    public ProfilePublic getSenderProfileById(final int id);
    public int getMessageCount();
    public void onMessageAdapterErrorOccurred(@NonNull final Error error);
}
