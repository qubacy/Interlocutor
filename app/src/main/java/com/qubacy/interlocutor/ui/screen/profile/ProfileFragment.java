package com.qubacy.interlocutor.ui.screen.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepository;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.FragmentBase;
import com.qubacy.interlocutor.ui.screen.profile.error.ProfileFragmentErrorEnum;
import com.qubacy.interlocutor.ui.screen.profile.model.ProfileFragmentViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

import java.io.Serializable;

public class ProfileFragment extends FragmentBase {
    public static final String C_PROFILE_DATA_REPOSITORY_ARG_NAME = "profileDataRepository";

    private ProfileFragmentViewModel m_profileFragmentViewModel = null;

    private EditText m_usernameEditText = null;
    private EditText m_contactEditText = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_profileFragmentViewModel =
                new ViewModelProvider(this).get(ProfileFragmentViewModel.class);

        if (!initWithArgs()) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    ProfileFragmentErrorEnum.NULL_ARGUMENTS.getResourceCode(),
                    ProfileFragmentErrorEnum.NULL_ARGUMENTS.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }


    }

    private boolean initWithArgs() {
        if (m_profileFragmentViewModel.isInitialized())
            return true;

        Bundle args = getArguments();

        if (args == null) return false;

        Serializable profileDataRepositorySerializable =
                args.getSerializable(C_PROFILE_DATA_REPOSITORY_ARG_NAME);

        if (!(profileDataRepositorySerializable instanceof ProfileDataRepository))
            return false;

        m_profileFragmentViewModel.
                setProfileDataRepository((ProfileDataRepository) profileDataRepositorySerializable);

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

        Profile profile = m_profileFragmentViewModel.getProfile(m_context);

        if (profile == null) {
            // todo: processing an error..

            return view;
        }

        String username = profile.getUsername();
        String contact = profile.getContact();

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

        ActivityUtility.setAppCompatActivityActionBarTitle(
                getActivity(), R.string.profile_status_bar_title);
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

        Profile profile = Profile.getInstance(username, contact);

        if (profile == null) {
            // todo: processing an error..

            return;
        }

        if (!m_profileFragmentViewModel.changeProfileData(profile, m_context)) {
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
