package com.qubacy.interlocutor.ui.screen.play.main.error;

import com.qubacy.interlocutor.R;

public enum PlayActivityErrorEnum {
    NULL_ARGUMENTS(
            R.string.error_play_activity_null_arguments, true),
    BROADCAST_RECEIVER_CREATION_FAILED(
            R.string.error_play_activity_broadcast_receiver_creation_failed, true),
    SERVICES_START_FAILED(
            R.string.error_play_activity_services_start_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayActivityErrorEnum(
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
