package com.qubacy.interlocutor.ui.screen.profile.error;

import com.qubacy.interlocutor.R;

public enum ProfileFragmentErrorEnum {
    NULL_ARGUMENTS(
            R.string.error_profile_fragment_null_arguments, true)
    ;

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private ProfileFragmentErrorEnum(
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
