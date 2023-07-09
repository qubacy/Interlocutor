package com.qubacy.interlocutor.ui.screen.play.choosing.broadcast.error;

import com.qubacy.interlocutor.R;

public enum PlayChoosingFragmentBroadcastErrorEnum {
    INCORRECT_COMMAND(
            R.string.error_play_choosing_fragment_broadcast_receiver_incorrect_command, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayChoosingFragmentBroadcastErrorEnum(
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
