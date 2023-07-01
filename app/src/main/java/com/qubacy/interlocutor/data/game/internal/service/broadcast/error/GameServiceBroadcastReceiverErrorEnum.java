package com.qubacy.interlocutor.data.game.internal.service.broadcast.error;

import com.qubacy.interlocutor.R;

public enum GameServiceBroadcastReceiverErrorEnum {
    INCORRECT_COMMAND(
            R.string.error_game_service_broadcast_receiver_incorrect_command, true),
    UNKNOWN_COMMAND_TYPE(
            R.string.error_game_service_broadcast_receiver_unknown_command_type, true),
    LACKING_PROFILE_DATA(
            R.string.error_game_service_broadcast_receiver_lacking_profile_data, true),
    INCORRECT_PROFILE_DATA(
            R.string.error_game_service_broadcast_receiver_incorrect_profile_data, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private GameServiceBroadcastReceiverErrorEnum(
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
