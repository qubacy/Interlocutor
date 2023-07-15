package com.qubacy.interlocutor.ui.screen.play.results.adapter.error;

import com.qubacy.interlocutor.R;

public enum PlayResultsUserContactAdapterErrorEnum {
    NULL_MATCHED_USER_PROFILE_DATA(
            R.string.error_play_results_user_contact_adapter_null_matched_user_profile_data, true),
    NULL_PROFILE_DATA(
            R.string.error_play_results_user_contact_adapter_null_profile_data, true),
    DATA_SETTING_FAILED(
            R.string.error_play_results_user_contact_adapter_data_setting_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayResultsUserContactAdapterErrorEnum(
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
