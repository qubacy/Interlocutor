package com.qubacy.interlocutor.data.profile;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.struct.profile.local.Profile;

import java.io.Serializable;

public interface ProfileDataSource extends Serializable {
    public String getUsername();
    public String getContact();
    public Profile getProfile();
    public boolean setUsername(@NonNull final String username);
    public boolean setContact(@NonNull final String contact);
    public boolean setProfile(@NonNull final Profile profile);
}
