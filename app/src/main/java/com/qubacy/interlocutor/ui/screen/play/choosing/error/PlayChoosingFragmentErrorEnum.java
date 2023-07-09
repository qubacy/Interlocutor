package com.qubacy.interlocutor.ui.screen.play.choosing.error;

import com.qubacy.interlocutor.R;

public enum PlayChoosingFragmentErrorEnum {
    BROADCAST_RECEIVER_CREATION_FAILED(
            R.string.error_play_choosing_fragment_broadcast_receiver_creation_failed, true),
    USER_ADAPTER_CREATION_FAILED(
            R.string.error_play_choosing_fragment_user_adapter_creation_failed, true)
    ;

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayChoosingFragmentErrorEnum(
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
