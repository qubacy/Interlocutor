package com.qubacy.interlocutor.ui.screen.play.choosing.adapter.error;

import com.qubacy.interlocutor.R;

public enum PlayChoosingUserAdapterErrorEnum {
    NULL_PROFILE_DATA(
            R.string.error_play_choosing_user_adapter_null_profile_data, true),
    SETTING_DATA_FAILED(
            R.string.error_play_choosing_user_adapter_setting_data_failed, true),
    USER_CHOSEN_STATE_SETTING_FAILED(
            R.string.error_play_choosing_user_adapter_user_chosen_state_setting_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayChoosingUserAdapterErrorEnum(
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
