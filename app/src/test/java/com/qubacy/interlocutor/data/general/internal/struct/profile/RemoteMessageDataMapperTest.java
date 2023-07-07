package com.qubacy.interlocutor.data.general.internal.struct.profile;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessageDataMapper;

import org.junit.Assert;
import org.junit.Test;

public class RemoteMessageDataMapperTest {
    @Test
    public void testRemoteMessageDataMapperCreation() {
        RemoteMessageDataMapper remoteMessageDataMapper =
                RemoteMessageDataMapper.getInstance();

        Assert.assertNotNull(remoteMessageDataMapper);
    }

    @Test
    public void testMappingNullRemoteMessageToMessage() {
        RemoteMessageDataMapper remoteMessageDataMapper =
                RemoteMessageDataMapper.getInstance();

        Assert.assertNotNull(remoteMessageDataMapper);

        Message message = remoteMessageDataMapper.map(null);

        Assert.assertNull(message);
    }

    @Test
    public void testMappingNotNullRemoteMessageToMessage() {
        RemoteMessageDataMapper remoteMessageDataMapper =
                RemoteMessageDataMapper.getInstance();

        Assert.assertNotNull(remoteMessageDataMapper);

        RemoteMessage remoteMessage = RemoteMessage.getInstance(0, "someText");

        Message message = remoteMessageDataMapper.map(remoteMessage);

        Assert.assertNotNull(message);
    }
}
