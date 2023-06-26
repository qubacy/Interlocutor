package com.qubacy.interlocutor.ui.screen.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.profile.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.local.ProfileDataStore;
import com.qubacy.interlocutor.ui.screen.profile.ProfileFragment;

public class MainMenuFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Navigation.
                        findNavController(v).
                        navigate(R.id.action_mainMenuFragment_to_playSearchingFragment);
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

    private void navigateToProfile(final View view) {
        ProfileDataRepository profileDataRepository =
                new ProfileDataStore.Builder().setContext(getContext()).build();
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

        if (!(getActivity() instanceof AppCompatActivity)) return;

        AppCompatActivity appCompatActivity = ((AppCompatActivity)getActivity());

        if (appCompatActivity == null) return;
        if (appCompatActivity.getSupportActionBar() == null) return;

        appCompatActivity.getSupportActionBar().hide();
    }
}
