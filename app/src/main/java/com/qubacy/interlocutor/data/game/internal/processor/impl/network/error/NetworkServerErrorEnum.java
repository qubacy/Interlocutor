package com.qubacy.interlocutor.data.game.internal.processor.impl.network.error;

import com.qubacy.interlocutor.R;

public enum NetworkServerErrorEnum {
    INCORRECT_PROFILE(
            0, R.string.error_game_session_processor_impl_network_incorrect_profile, true);

    private int m_id;
    private int m_stringResId;
    private boolean m_isCritical;

    private NetworkServerErrorEnum(
            final int id,
            final int stringResId,
            final boolean isCritical)
    {
        m_id = id;
        m_stringResId = stringResId;
        m_isCritical = isCritical;
    }

    public int getId() {
        return m_id;
    }

    public int getStringResId() {
        return m_stringResId;
    }

    public boolean isCritical() {
        return m_isCritical;
    }

    public static NetworkServerErrorEnum getNetworkServerErrorById(
            final int id)
    {
        if (id < 0) return null;

        for (final NetworkServerErrorEnum networkServerError : NetworkServerErrorEnum.values()) {
            if (networkServerError.getId() == id) return networkServerError;
        }

        return null;
    }
}
