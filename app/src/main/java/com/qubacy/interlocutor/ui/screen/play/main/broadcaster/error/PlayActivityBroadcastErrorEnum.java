package com.qubacy.interlocutor.ui.screen.play.main.broadcaster.error;

import com.qubacy.interlocutor.R;

public enum PlayActivityBroadcastErrorEnum {
    INCORRECT_COMMAND(
            R.string.error_play_activity_broadcast_receiver_incorrect_command, true),
    LACKING_DISCONNECTION_DATA(
            R.string.error_play_activity_broadcast_receiver_lacking_disconnection_data, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayActivityBroadcastErrorEnum(
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
