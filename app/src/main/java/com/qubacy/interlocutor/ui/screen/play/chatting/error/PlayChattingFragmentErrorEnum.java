package com.qubacy.interlocutor.ui.screen.play.chatting.error;

import com.qubacy.interlocutor.R;

public enum PlayChattingFragmentErrorEnum {
    BROADCAST_RECEIVER_CREATION_FAILED(
            R.string.error_play_chatting_fragment_broadcast_receiver_creation_failed, true),
    SENDING_MESSAGE_CREATION_FAILED(
            R.string.error_play_chatting_fragment_sending_message_creation_failed, true),
    MESSAGE_ADAPTER_CREATION_FAILED(
            R.string.error_play_chatting_fragment_message_adapter_creation_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayChattingFragmentErrorEnum(
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
