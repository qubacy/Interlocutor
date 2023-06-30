package com.qubacy.interlocutor.ui.main.error;

import com.qubacy.interlocutor.R;

public enum MainActivityErrorEnum {
    BROADCAST_RECEIVER_CREATION_FAILED(
            R.string.error_main_activity_broadcast_receiver_creation_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private MainActivityErrorEnum(
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
