package com.qubacy.interlocutor.ui.screen.play.results.error;

import com.qubacy.interlocutor.R;

public enum PlayResultsFragmentErrorEnum {
    ADAPTER_CREATION_FAILED(
            R.string.error_play_results_fragment_adapter_creation_failed, true);

    private final int m_resourceCode;
    private final boolean m_isCritical;

    private PlayResultsFragmentErrorEnum(
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
