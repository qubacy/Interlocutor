package com.qubacy.interlocutor.ui.screen.play.chatting.model;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public interface PlayChattingViewModel {
//    public Message getMessageByIndex(final int index);
//    public int getMessageCount();
//    public void addMessage(@NonNull final Message message);
    public ProfilePublic getProfileById(final int id);
    public String getTopic();
    public long getChattingDuration();
}
