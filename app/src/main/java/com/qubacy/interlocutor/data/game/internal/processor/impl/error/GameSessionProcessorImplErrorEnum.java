package com.qubacy.interlocutor.data.game.internal.processor.impl.error;

import com.qubacy.interlocutor.R;

public enum GameSessionProcessorImplErrorEnum {
    UNKNOWN_OPERATION_TYPE(
            R.string.error_game_session_processor_impl_unknown_operation_type, true),
    NULL_SERVER_MESSAGE(
            R.string.error_game_session_processor_impl_null_server_message, true),
    NULL_SERVER_MESSAGE_BODY(
            R.string.error_game_session_processor_impl_null_server_message_body, true),
    SENDING_CLIENT_MESSAGE_FAILED(
            R.string.error_game_session_processor_impl_sending_client_message_failed, true),
    UNKNOWN_NETWORK_CALLBACK_COMMAND_TYPE(
            R.string.error_game_session_processor_impl_unknown_network_callback_command_type, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private GameSessionProcessorImplErrorEnum(
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
