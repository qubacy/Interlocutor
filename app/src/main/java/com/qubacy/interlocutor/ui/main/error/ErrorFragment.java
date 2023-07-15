package com.qubacy.interlocutor.ui.main.error;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.ui.common.fragment.FragmentBase;

public class ErrorFragment extends FragmentBase {
    private static final String C_ERROR_ARG_NAME = "error";

    private Error m_error = null;

    public ErrorFragment() {
        super();
    }

    protected ErrorFragment(final Error error) {
        super();

        m_error = error;
    }

    public static ErrorFragment getInstance(
            final Error error)
    {
        if (error == null) return null;

        ErrorFragment errorDialogFragment = new ErrorFragment(error);
        Bundle args = new Bundle();

        args.putSerializable(C_ERROR_ARG_NAME, error);
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
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        TextView messageTextView = view.findViewById(R.id.error_message);
        Button okButton = view.findViewById(R.id.error_button_ok);

        messageTextView.setText(m_error.getMessage());
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onOkClicked();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        outState.putSerializable(C_ERROR_ARG_NAME, m_error);

        super.onSaveInstanceState(outState);
    }

    private void onOkClicked() {
        getActivity().onBackPressed();
    }

    public Error getError() {
        return m_error;
    }
}
