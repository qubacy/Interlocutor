package com.qubacy.interlocutor.data.profile.internal.repository;

import android.content.Context;

import com.qubacy.interlocutor.data.profile.export.repository.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;
import com.qubacy.interlocutor.data.profile.internal.source.ProfileDataSourceImpl;

import java.io.Serializable;

public class ProfileDataRepositoryImpl
    implements
        ProfileDataRepository,
        Serializable
{
    protected ProfileDataRepositoryImpl() {

    }

    public static ProfileDataRepositoryImpl getInstance() {
        return new ProfileDataRepositoryImpl();
    }

    public ProfileDataSource getSource(final Context context) {
        ProfileDataSourceImpl profileDataSource =
                new ProfileDataSourceImpl.Builder().setContext(context).build();

        return profileDataSource;
    }
}
