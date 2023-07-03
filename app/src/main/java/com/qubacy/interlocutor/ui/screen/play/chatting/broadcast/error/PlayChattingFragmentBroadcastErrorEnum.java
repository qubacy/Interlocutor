package com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.error;

import com.qubacy.interlocutor.R;

public enum PlayChattingFragmentBroadcastErrorEnum {
    INCORRECT_COMMAND(
            R.string.error_play_chatting_fragment_broadcast_receiver_incorrect_command, true),
    LACKING_MESSAGE_DATA(
            R.string.error_play_chatting_fragment_broadcast_receiver_lacking_message_data, true),
    INCORRECT_MESSAGE_DATA(
            R.string.error_play_chatting_fragment_broadcast_receiver_incorrect_message_data, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayChattingFragmentBroadcastErrorEnum(
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
