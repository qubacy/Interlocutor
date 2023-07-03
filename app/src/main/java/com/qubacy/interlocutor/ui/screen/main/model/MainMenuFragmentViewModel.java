package com.qubacy.interlocutor.ui.screen.main.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncherFactory;
import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepositoryFactory;
import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;

public class MainMenuFragmentViewModel extends ViewModel {
    private ProfileDataRepository m_profileDataRepository = null;

    public ProfileDataRepository getProfileDataRepository() {
        return m_profileDataRepository;
    }

    public boolean initProfileDataRepository() {
        ProfileDataRepositoryFactory profileDataRepositoryFactory =
                ProfileDataRepositoryFactory.getInstance();

        if (profileDataRepositoryFactory == null) return false;

        ProfileDataRepository profileDataRepository =
                profileDataRepositoryFactory.generateProfileDataRepository();

        if (profileDataRepository == null) return false;

        m_profileDataRepository = profileDataRepository;

        return true;
    }

    public ProfileDataSource getProfileDataSource(@NonNull final Context context) {
        ProfileDataSource profileDataSource =
                m_profileDataRepository.getSource(context);

        if (profileDataSource == null) return null;

        return profileDataSource;
    }

    public GameServiceLauncher getGameServiceLauncher() {
        GameServiceLauncherFactory gameServiceLauncherFactory =
                GameServiceLauncherFactory.getInstance();

        if (gameServiceLauncherFactory == null) return null;

        GameServiceLauncher gameServiceLauncher =
                gameServiceLauncherFactory.generateGameServiceLauncher();

        if (gameServiceLauncher == null) return null;

        return gameServiceLauncher;
    }
}
