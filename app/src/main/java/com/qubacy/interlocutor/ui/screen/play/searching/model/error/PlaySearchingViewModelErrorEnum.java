package com.qubacy.interlocutor.ui.screen.play.searching.model.error;

import com.qubacy.interlocutor.R;

public enum PlaySearchingViewModelErrorEnum {
    ;

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlaySearchingViewModelErrorEnum(
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
