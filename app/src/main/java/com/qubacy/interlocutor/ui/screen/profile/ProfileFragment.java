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
import androidx.fragment.app.Fragment;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.profile.error.ProfileFragmentErrorEnum;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

import java.io.Serializable;

public class ProfileFragment extends Fragment {
    public static final String C_PROFILE_DATA_REPOSITORY_ARG_NAME = "profileDataRepository";

    private Context m_context = null;

    private EditText m_usernameEditText = null;
    private EditText m_contactEditText = null;

    private ProfileDataRepository m_profileDataRepository = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Bundle args = getArguments();

        if (args == null) return false;

        Serializable profileDataRepositorySerializable =
                args.getSerializable(C_PROFILE_DATA_REPOSITORY_ARG_NAME);

        if (!(profileDataRepositorySerializable instanceof ProfileDataRepository))
            return false;

        m_profileDataRepository = (ProfileDataRepository) profileDataRepositorySerializable;

        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        m_context = context;
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

        ProfileDataSource profileDataSource = m_profileDataRepository.getSource(getContext());

        if (profileDataSource == null) {
            // todo: processing an error..

            return view;
        }

        String username = profileDataSource.getUsername();
        String contact = profileDataSource.getContact();

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

        ProfileDataSource profileDataSource = m_profileDataRepository.getSource(getContext());

        if (profileDataSource == null) {
            // todo: processing an error..

            return;
        }

        if (!profileDataSource.setUsername(username)
         || !profileDataSource.setContact(contact))
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
