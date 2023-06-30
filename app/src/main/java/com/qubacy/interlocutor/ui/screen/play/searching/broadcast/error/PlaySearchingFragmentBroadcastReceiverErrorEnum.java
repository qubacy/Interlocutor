package com.qubacy.interlocutor.ui.screen.play.searching.broadcast.error;

import com.qubacy.interlocutor.R;

public enum PlaySearchingFragmentBroadcastReceiverErrorEnum {
    INCORRECT_COMMAND(
            R.string.error_play_searching_fragment_broadcast_receiver_incorrect_command, true),
    LACKING_FOUND_GAME_DATA(
            R.string.error_play_searching_fragment_broadcast_receiver_lacking_found_game_data, true),
    INCORRECT_FOUND_GAME_DATA(
            R.string.error_play_searching_fragment_broadcast_receiver_incorrect_found_game_data, true)
    ;

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlaySearchingFragmentBroadcastReceiverErrorEnum(
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
