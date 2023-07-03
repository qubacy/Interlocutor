package com.qubacy.interlocutor.ui.screen.profile.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;

public class ProfileFragmentViewModel extends ViewModel {
    private ProfileDataRepository m_profileDataRepository = null;

    public ProfileDataRepository getProfileDataRepository() {
        return m_profileDataRepository;
    }

    public boolean setProfileDataRepository(
            @NonNull final ProfileDataRepository profileDataRepository)
    {
        m_profileDataRepository = profileDataRepository;

        return true;
    }

    public boolean isInitialized() {
        return (m_profileDataRepository != null);
    }

    public Profile getProfile(@NonNull final Context context) {
        ProfileDataSource profileDataSource = getProfileDataSource(context);

        if (profileDataSource == null) return null;

        return profileDataSource.getProfile();
    }

    public boolean changeProfileData(
            @NonNull final Profile profile,
            @NonNull final Context context)
    {
        ProfileDataSource profileDataSource = getProfileDataSource(context);

        if (profileDataSource == null) return false;
        if (!profileDataSource.setProfile(profile)) return false;

        return true;
    }

    private ProfileDataSource getProfileDataSource(@NonNull final Context context) {
        return m_profileDataRepository.getSource(context);
    }
}
