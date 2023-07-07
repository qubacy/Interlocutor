package com.qubacy.interlocutor.ui.screen.play.choosing.model;

import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.ui.screen.play.common.task.TextViewTimerAsyncTaskCallback;

public class PlayChoosingFragmentViewModel extends ViewModel
    implements TextViewTimerAsyncTaskCallback
{
    private Long m_remainingChoosingTime = null;

    @Override
    public Long getRemainingTime() {
        return m_remainingChoosingTime;
    }

    @Override
    public boolean setRemainingTime(final Long newRemainingTime) {
        if (newRemainingTime == null) return false;

        m_remainingChoosingTime = newRemainingTime;

        return true;
    }
}
