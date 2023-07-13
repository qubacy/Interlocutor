package com.qubacy.interlocutor.ui.common.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class FragmentBase extends Fragment {
    protected Context m_context = null;

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);

        m_context = context;
    }
}
