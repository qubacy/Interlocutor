package com.qubacy.interlocutor.data.profile.export.repository;

import com.qubacy.interlocutor.data.profile.internal.repository.ProfileDataRepositoryImpl;

public class ProfileDataRepositoryFactory {
    protected ProfileDataRepositoryFactory() {

    }

    public static ProfileDataRepositoryFactory getInstance() {
        return new ProfileDataRepositoryFactory();
    }

    public ProfileDataRepository generateProfileDataRepository() {
        return ProfileDataRepositoryImpl.getInstance();
    }
}
