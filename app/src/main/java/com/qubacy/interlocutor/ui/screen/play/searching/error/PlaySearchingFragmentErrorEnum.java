package com.qubacy.interlocutor.ui.screen.play.searching.error;

import com.qubacy.interlocutor.R;

public enum PlaySearchingFragmentErrorEnum {
    BROADCAST_RECEIVER_CREATION_FAILED(
            R.string.error_play_searching_fragment_broadcast_receiver_creation_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlaySearchingFragmentErrorEnum(
            final int resourceCode,
            final boolean isCritical)
    {
        m_resourceCode = resourceCode;
        m_isCritical = isCritical;
    }

    public int getResourceCode() {
        return m_resourceCode;
    }

    public boolean isCritical() {
        return m_isCritical;
    }
}
