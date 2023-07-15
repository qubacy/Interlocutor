package com.qubacy.interlocutor.ui.screen.main.error;

import com.qubacy.interlocutor.R;

public enum MainMenuFragmentErrorEnum {
    NULL_PROFILE_DATA_SOURCE(
            R.string.error_main_menu_fragment_null_profile_data_source, true),
    NULL_GAME_SERVICE_LAUNCHER(
            R.string.error_main_menu_fragment_null_game_service_launcher, true),
    NULL_PROFILE_DATA_REPOSITORY(
            R.string.error_main_menu_fragment_null_profile_data_repository, true),
    NULL_PROFILE_DATA(
            R.string.error_main_menu_fragment_null_profile_data, false);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private MainMenuFragmentErrorEnum(
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
