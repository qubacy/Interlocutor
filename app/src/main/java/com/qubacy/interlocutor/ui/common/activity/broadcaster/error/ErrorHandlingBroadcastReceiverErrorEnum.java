package com.qubacy.interlocutor.ui.common.activity.broadcaster.error;

import com.qubacy.interlocutor.R;

public enum ErrorHandlingBroadcastReceiverErrorEnum {
    UNKNOWN_COMMAND_TYPE(
            R.string.error_error_handling_broadcast_receiver_unknown_command_type, true),
    LACKING_ERROR_DATA(
            R.string.error_error_handling_broadcast_receiver_lacking_error_data, true),
    INCORRECT_ERROR_DATA(
            R.string.error_error_handling_broadcast_receiver_incorrect_error_data, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private ErrorHandlingBroadcastReceiverErrorEnum(
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
