package com.qubacy.interlocutor.ui.screen.play.choosing.model;

import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.ui.screen.play.common.task.TextViewTimerAsyncTaskCallback;

import java.util.ArrayList;
import java.util.List;

public class PlayChoosingFragmentViewModel extends ViewModel
    implements TextViewTimerAsyncTaskCallback
{
    private Long m_remainingChoosingTime = null;
    private final List<Integer> m_chosenUserIdList;

    private boolean m_isChoiceMade = false;

    public PlayChoosingFragmentViewModel() {
        m_chosenUserIdList = new ArrayList<>();
    }

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

    public List<Integer> getChosenUserIdList() {
        return m_chosenUserIdList;
    }

    public boolean isUserChosenById(final int userId) {
        for (final int chosenUserId : m_chosenUserIdList) {
            if (userId == chosenUserId) return true;
        }

        return false;
    }

    public boolean addChosenUserId(final int userId) {
        for (final int chosenUserId : m_chosenUserIdList) {
            if (userId == chosenUserId) return true;
        }

        m_chosenUserIdList.add(userId);

        return true;
    }

    public boolean removeChosenUserId(final int userId) {
        for (final Integer chosenUserId : m_chosenUserIdList) {
            if (userId == chosenUserId.intValue()) {
                m_chosenUserIdList.remove(chosenUserId);

                return true;
            }
        }

        return false;
    }

    public boolean isChoiceMade() {
        return m_isChoiceMade;
    }

    public void setChoiceMade(final boolean isChoiceMade) {
        m_isChoiceMade = isChoiceMade;
    }
}
