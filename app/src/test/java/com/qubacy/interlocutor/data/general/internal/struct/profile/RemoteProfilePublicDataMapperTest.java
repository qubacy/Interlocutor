package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

import org.junit.Assert;
import org.junit.Test;

public class RemoteProfilePublicDataMapperTest {
    @Test
    public void testRemoteProfilePublicDataMapperCreation() {
        RemoteProfilePublicDataMapper remoteProfilePublicDataMapper =
                RemoteProfilePublicDataMapper.getInstance();

        Assert.assertNotNull(remoteProfilePublicDataMapper);
    }

    @Test
    public void testMappingNullRemoteProfilePublicToProfilePublic() {
        RemoteProfilePublicDataMapper remoteProfilePublicDataMapper =
                RemoteProfilePublicDataMapper.getInstance();

        Assert.assertNotNull(remoteProfilePublicDataMapper);

        ProfilePublic profilePublic = remoteProfilePublicDataMapper.map(null);

        Assert.assertNull(profilePublic);
    }

    @Test
    public void testMappingNotNullRemoteProfilePublicToProfilePublic() {
        RemoteProfilePublicDataMapper remoteProfilePublicDataMapper =
                RemoteProfilePublicDataMapper.getInstance();

        Assert.assertNotNull(remoteProfilePublicDataMapper);

        RemoteProfilePublic remoteProfilePublic =
                RemoteProfilePublic.getInstance(0, "username");

        ProfilePublic profilePublic = remoteProfilePublicDataMapper.map(remoteProfilePublic);

        Assert.assertNotNull(profilePublic);
    }
}
