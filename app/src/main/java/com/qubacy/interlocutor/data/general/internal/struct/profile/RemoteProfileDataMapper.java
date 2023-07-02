package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.DataMapper;

public class RemoteProfileDataMapper implements DataMapper<RemoteProfile, Profile> {

    protected RemoteProfileDataMapper() {

    }

    public static RemoteProfileDataMapper getInstance() {
        return new RemoteProfileDataMapper();
    }

    @Override
    public Profile map(final RemoteProfile input) {
        if (input == null) return null;

        return Profile.getInstance(
                input.getId(), input.getUsername(), input.getContact());
    }
}
