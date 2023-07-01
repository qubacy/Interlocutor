package com.qubacy.interlocutor.data.profile.export.repository;

import android.content.Context;

import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;

import java.io.Serializable;

public interface ProfileDataRepository extends Serializable {
    public ProfileDataSource getSource(final Context context);
}
