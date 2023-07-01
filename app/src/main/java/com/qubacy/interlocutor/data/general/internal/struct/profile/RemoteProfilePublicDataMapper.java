package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.data.general.internal.struct.DataMapper;

public class RemoteProfilePublicDataMapper
        implements DataMapper<RemoteProfilePublic, ProfilePublic>
{
    protected RemoteProfilePublicDataMapper() {

    }

    public static RemoteProfilePublicDataMapper getInstance() {
        return new RemoteProfilePublicDataMapper();
    }

    @Override
    public ProfilePublic map(final RemoteProfilePublic input) {
        if (input == null) return null;

        return ProfilePublic.getInstance(input.getId(), input.getUsername());
    }
}
