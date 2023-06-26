package com.qubacy.interlocutor.ui.screen.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.profile.ProfileDataRepository;

import java.io.Serializable;

public class ProfileFragment extends Fragment {
    public static final String C_PROFILE_DATA_REPOSITORY_ARG_NAME = "profileDataRepository";

    private EditText m_usernameEditText = null;
    private EditText m_contactEditText = null;

    private ProfileDataRepository m_profileDataRepository = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!initWithArgs()) {
            // todo: process args' lacking situation..

            return;
        }


    }

    private boolean initWithArgs() {
        Bundle args = getArguments();

        if (args == null) return false;

        Serializable profileDataRepositorySerializable =
                args.getSerializable(C_PROFILE_DATA_REPOSITORY_ARG_NAME);

        if (!(profileDataRepositorySerializable instanceof ProfileDataRepository))
            return false;

        ProfileDataRepository profileDataRepository =
                (ProfileDataRepository) profileDataRepositorySerializable;

        if (profileDataRepository == null) return false;

        m_profileDataRepository = profileDataRepository;

        return true;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        m_usernameEditText = view.findViewById(R.id.profile_username_input);
        m_contactEditText = view.findViewById(R.id.profile_contact_input);

        String username = m_profileDataRepository.getUsername();
        String contact = m_profileDataRepository.getContact();

        if (username != null && contact != null) {
            m_usernameEditText.setText(username);
            m_contactEditText.setText(contact);
        }

        Button button = view.findViewById(R.id.profile_confirm_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo: saving changes..

                confirmProfileChanges();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (!(getActivity() instanceof AppCompatActivity)) return;

        AppCompatActivity appCompatActivity = ((AppCompatActivity)getActivity());

        if (appCompatActivity == null) return;

        ActionBar actionBar = appCompatActivity.getSupportActionBar();

        if (actionBar == null) return;

        actionBar.setTitle(R.string.profile_status_bar_title);
        actionBar.show();
    }

    private void confirmProfileChanges() {
        String username = m_usernameEditText.getText().toString();
        String contact = m_contactEditText.getText().toString();

        if (username.isEmpty() || contact.isEmpty()) {
            Toast.makeText(
                    getContext(),
                    R.string.profile_confirm_error_message_invalid_data,
                    Toast.LENGTH_SHORT).
                    show();

            return;
        }

        if (!m_profileDataRepository.setUsername(username)
         || !m_profileDataRepository.setContact(contact))
        {
            Toast.makeText(
                            getContext(),
                            R.string.profile_confirm_error_message_saving_failed,
                            Toast.LENGTH_SHORT).
                    show();

            return;
        }

        Toast.makeText(
                        getContext(),
                        R.string.profile_confirm_error_message_saving_succeed,
                        Toast.LENGTH_SHORT).
                show();
    }
}
