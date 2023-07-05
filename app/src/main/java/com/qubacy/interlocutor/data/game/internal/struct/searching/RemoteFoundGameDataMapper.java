package com.qubacy.interlocutor.data.game.internal.struct.searching;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.data.general.internal.struct.DataMapper;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublicDataMapper;

import java.util.ArrayList;
import java.util.List;

public class RemoteFoundGameDataMapper
        implements DataMapper<RemoteFoundGameData, FoundGameData>
{
    private final RemoteProfilePublicDataMapper m_remoteProfilePublicDataMapper;

    protected RemoteFoundGameDataMapper(
            final RemoteProfilePublicDataMapper remoteProfilePublicDataMapper)
    {
        m_remoteProfilePublicDataMapper = remoteProfilePublicDataMapper;
    }

    public static RemoteFoundGameDataMapper getInstance(
            final RemoteProfilePublicDataMapper remoteProfilePublicDataMapper)
    {
        if (remoteProfilePublicDataMapper == null)
            return null;

        return new RemoteFoundGameDataMapper(remoteProfilePublicDataMapper);
    }

    @Override
    public FoundGameData map(final RemoteFoundGameData input) {
        List<RemoteProfilePublic> remoteProfilePublicList = input.getProfilePublicList();
        List<ProfilePublic> profilePublicList = new ArrayList<>();

        for (final RemoteProfilePublic remoteProfilePublic : remoteProfilePublicList) {
            ProfilePublic profilePublic =
                    m_remoteProfilePublicDataMapper.map(remoteProfilePublic);

            if (profilePublic == null) return null;

            profilePublicList.add(profilePublic);
        }

        return FoundGameData.getInstance(
                input.getLocalProfileId(),
                input.getStartSessionTime(),
                input.getChattingStageDuration(),
                input.getChoosingStageDuration(),
                profilePublicList);
    }
}
