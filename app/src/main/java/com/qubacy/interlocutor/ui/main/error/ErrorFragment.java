package com.qubacy.interlocutor.ui.main.error;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.ui.common.fragment.SystemMessageDialogFragment;

public class ErrorFragment extends SystemMessageDialogFragment {
    private static final String C_ERROR_ARG_NAME = "error";

    private Error m_error = null;

    public ErrorFragment() {
        super();
    }

    protected ErrorFragment(final Error error) {
        super(error.getMessage());

        m_error = error;
    }

    public static ErrorFragment getInstance(
            final Error error)
    {
        if (error == null) return null;

        ErrorFragment errorDialogFragment = new ErrorFragment(error);
        Bundle args = new Bundle();

        args.putSerializable(C_ERROR_ARG_NAME, error);
        args.putString(C_TEXT_ARG_NAME, error.getMessage());
        errorDialogFragment.setArguments(args);

        return errorDialogFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Error error =
                    (Error) savedInstanceState.getSerializable(C_ERROR_ARG_NAME);

            if (error == null) {
                onOkClicked();

                return;
            }

            m_error = error;
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        outState.putSerializable(C_ERROR_ARG_NAME, m_error);

        super.onSaveInstanceState(outState);
    }

    public Error getError() {
        return m_error;
    }
}
