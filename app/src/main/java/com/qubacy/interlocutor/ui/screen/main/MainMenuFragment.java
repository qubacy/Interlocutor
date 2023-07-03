package com.qubacy.interlocutor.ui.screen.main;

import static com.qubacy.interlocutor.ui.screen.play.main.PlayActivity.C_GAME_SERVICE_LAUNCHER_ARG_NAME;
import static com.qubacy.interlocutor.ui.screen.play.main.PlayActivity.C_PROFILE_ARG_NAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.FragmentBase;
import com.qubacy.interlocutor.ui.screen.main.error.MainMenuFragmentErrorEnum;
import com.qubacy.interlocutor.ui.screen.main.model.MainMenuFragmentViewModel;
import com.qubacy.interlocutor.ui.screen.profile.ProfileFragment;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public class MainMenuFragment extends FragmentBase {
    private MainMenuFragmentViewModel m_mainMenuFragmentViewModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_mainMenuFragmentViewModel =
                new ViewModelProvider(this).get(MainMenuFragmentViewModel.class);

        if (!m_mainMenuFragmentViewModel.initProfileDataRepository()) return;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Button playButton = view.findViewById(R.id.main_menu_button_play);
        Button profileButton = view.findViewById(R.id.main_menu_button_profile);
        Button exitButton = view.findViewById(R.id.main_menu_button_exit);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPlaySearching(v);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfile(v);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() == null) return;

                getActivity().finishAndRemoveTask();
            }
        });

        return view;
    }

    private void navigateToPlaySearching(final View view) {
        ProfileDataSource profileDataSource =
                m_mainMenuFragmentViewModel.getProfileDataSource(m_context);

        if (profileDataSource == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(m_context,
                    MainMenuFragmentErrorEnum.NULL_PROFILE_DATA_SOURCE.getResourceCode(),
                    MainMenuFragmentErrorEnum.NULL_PROFILE_DATA_SOURCE.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        GameServiceLauncher gameServiceLauncher =
                m_mainMenuFragmentViewModel.getGameServiceLauncher();

        if (gameServiceLauncher == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(m_context,
                    MainMenuFragmentErrorEnum.NULL_GAME_SERVICE_LAUNCHER.getResourceCode(),
                    MainMenuFragmentErrorEnum.NULL_GAME_SERVICE_LAUNCHER.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        Bundle args = new Bundle();

        args.putSerializable(
                C_PROFILE_ARG_NAME, profileDataSource.getProfile());
        args.putSerializable(
                C_GAME_SERVICE_LAUNCHER_ARG_NAME, gameServiceLauncher);

        Navigation.
                findNavController(view).
                navigate(R.id.action_mainMenuFragment_to_playActivity, args);
    }

    private void navigateToProfile(final View view) {
        ProfileDataRepository profileDataRepository =
                m_mainMenuFragmentViewModel.getProfileDataRepository();

        if (profileDataRepository == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(m_context,
                    MainMenuFragmentErrorEnum.NULL_PROFILE_DATA_REPOSITORY.getResourceCode(),
                    MainMenuFragmentErrorEnum.NULL_PROFILE_DATA_REPOSITORY.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        Bundle args = new Bundle();

        args.putSerializable(
                ProfileFragment.C_PROFILE_DATA_REPOSITORY_ARG_NAME, profileDataRepository);

        Navigation.
                findNavController(view).
                navigate(R.id.action_mainMenuFragment_to_profileFragment, args);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ActivityUtility.hideAppCompatActivityActionBar(getActivity());
    }
}
