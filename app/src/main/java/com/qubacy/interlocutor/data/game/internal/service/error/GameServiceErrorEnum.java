package com.qubacy.interlocutor.data.game.internal.service.error;

import com.qubacy.interlocutor.R;

public enum GameServiceErrorEnum {
    LACKING_GAME_PROCESSOR(
            R.string.error_game_service_lacking_game_processor, true),
    BROADCAST_RECEIVER_CREATION_FAILED(
            R.string.error_game_service_broadcast_receiver_creation_failed, true),
    STARTING_SEARCHING_FAILED(
            R.string.error_game_service_starting_searching_failed, true),
    STOPPING_SEARCHING_FAILED(
            R.string.error_game_service_stopping_searching_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private GameServiceErrorEnum(
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
