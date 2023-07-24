package com.qubacy.interlocutor.ui.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qubacy.interlocutor.R;

public class SystemMessageDialogFragment extends FragmentBase {
    protected static final String C_TEXT_ARG_NAME = "text";

    protected String m_text = null;

    public SystemMessageDialogFragment() {
        super();
    }

    protected SystemMessageDialogFragment(final String text) {
        super();

        m_text = text;
    }

    public static SystemMessageDialogFragment getInstance(
            final String text)
    {
        if (text == null) return null;
        if (text.isEmpty()) return null;

        SystemMessageDialogFragment errorDialogFragment =
                new SystemMessageDialogFragment(text);
        Bundle args = new Bundle();

        args.putSerializable(C_TEXT_ARG_NAME, text);
        errorDialogFragment.setArguments(args);

        return errorDialogFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String text = savedInstanceState.getString(C_TEXT_ARG_NAME);

            if (text == null) {
                onOkClicked();

                return;
            }

            m_text = text;
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_system_message_dialog, container, false);

        TextView messageTextView = view.findViewById(R.id.error_message);
        Button okButton = view.findViewById(R.id.error_button_ok);

        messageTextView.setText(m_text);
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
        outState.putSerializable(C_TEXT_ARG_NAME, m_text);

        super.onSaveInstanceState(outState);
    }

    protected void onOkClicked() {
        getActivity().onBackPressed();
    }
}
