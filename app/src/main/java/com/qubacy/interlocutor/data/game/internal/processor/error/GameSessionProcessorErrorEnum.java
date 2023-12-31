package com.qubacy.interlocutor.data.game.internal.processor.error;

import com.qubacy.interlocutor.R;

public enum GameSessionProcessorErrorEnum {
    UNKNOWN_COMMAND_TYPE(
            R.string.error_game_session_processor_unknown_command, true),
    SEARCHING_STATE_CREATION_FAILED(
            R.string.error_game_session_processor_searching_state_creation_failed, true),
    CHATTING_STATE_CREATION_FAILED(
            R.string.error_game_session_processor_chatting_state_creation_failed, true),
    CHOOSING_STATE_CREATION_FAILED(
            R.string.error_game_session_processor_choosing_state_creation_failed, true),
    RESULTS_STATE_CREATION_FAILED(
            R.string.error_game_session_processor_results_state_creation_failed, true),
    UNKNOWN_STATE_TYPE(
            R.string.error_game_session_processor_unknown_state, true),
    ILLEGAL_STATE(
            R.string.error_game_session_processor_illegal_state, true),
    CHOSEN_USERS_SETTING_FAILED(
            R.string.error_game_session_processor_chosen_users_setting_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private GameSessionProcessorErrorEnum(
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
