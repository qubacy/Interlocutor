package com.qubacy.interlocutor.ui.screen.play.chatting.adapter.error;

import com.qubacy.interlocutor.R;

public enum PlayChattingMessageAdapterErrorEnum {
    NULL_MESSAGE(
            R.string.error_play_chatting_message_adapter_null_message, true),
    NULL_SENDER_PROFILE(
            R.string.error_play_chatting_message_adapter_null_sender_profile, true),
    SETTING_VIEW_HOLDER_DATA_FAILED(
            R.string.error_play_chatting_message_adapter_setting_view_holder_data_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayChattingMessageAdapterErrorEnum(
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
