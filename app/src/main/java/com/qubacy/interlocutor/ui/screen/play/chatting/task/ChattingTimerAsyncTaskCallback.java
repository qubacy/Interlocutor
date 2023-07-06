package com.qubacy.interlocutor.ui.screen.play.chatting.task;

public interface ChattingTimerAsyncTaskCallback {
    public Long getChattingTimeRemaining();
    public boolean setChattingTimeRemaining(final Long newChattingTimeRemaining);
}
