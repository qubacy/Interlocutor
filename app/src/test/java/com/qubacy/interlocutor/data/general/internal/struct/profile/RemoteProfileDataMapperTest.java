package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

import org.junit.Assert;
import org.junit.Test;

public class RemoteProfileDataMapperTest {
    @Test
    public void testRemoteProfileDataMapperCreation() {
        RemoteProfileDataMapper remoteProfileDataMapper =
                RemoteProfileDataMapper.getInstance();

        Assert.assertNotNull(remoteProfileDataMapper);
    }

    @Test
    public void testMappingNullRemoteProfileToProfile() {
        RemoteProfileDataMapper remoteProfileDataMapper =
                RemoteProfileDataMapper.getInstance();

        Assert.assertNotNull(remoteProfileDataMapper);

        Profile profile = remoteProfileDataMapper.map(null);

        Assert.assertNull(profile);
    }

    @Test
    public void testMappingNotNullRemoteProfileToProfile() {
        RemoteProfileDataMapper remoteProfileDataMapper =
                RemoteProfileDataMapper.getInstance();

        Assert.assertNotNull(remoteProfileDataMapper);

        RemoteProfile remoteProfile =
                RemoteProfile.getInstance(0, "username", "contact");

        Profile profile = remoteProfileDataMapper.map(remoteProfile);

        Assert.assertNotNull(profile);
    }
}
