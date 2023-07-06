package com.qubacy.interlocutor.ui.screen.play.main.model;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.chatting.model.PlayChattingViewModel;
import com.qubacy.interlocutor.ui.screen.play.model.PlayViewModel;
import com.qubacy.interlocutor.ui.screen.play.searching.model.PlaySearchingViewModel;

import java.util.List;

public class PlayFullViewModel extends PlayViewModel
    implements
        PlaySearchingViewModel,
        PlayChattingViewModel
{
    protected Profile m_profile = null;
    protected FoundGameData m_foundGameData = null;

//    protected final List<Message> m_messageList;

    public PlayFullViewModel() {
//        m_messageList = new LinkedList<>();
    }

    public boolean setProfile(@NonNull final Profile profile) {
        m_profile = profile;

        return true;
    }

    @Override
    public Profile getProfile() {
        return m_profile;
    }

    @Override
    public boolean setFoundGameData(@NonNull final FoundGameData foundGameData) {
        m_foundGameData = foundGameData;

        return true;
    }

    @Override
    public ProfilePublic getProfileById(final int id) {
        if (m_foundGameData.getLocalProfileId() == id)
            return m_profile;

        List<ProfilePublic> profilePublicList =
                m_foundGameData.getProfilePublicList();

        if (id < 0) return null;

        for (final ProfilePublic profilePublic : profilePublicList)
            if (profilePublic.getId() == id) return profilePublic;

        return null;
    }

    @Override
    public String getTopic() {
        return m_foundGameData.getChattingTopic();
    }

    @Override
    public long getChattingDuration() {
        return m_foundGameData.getChattingStageDuration();
    }
}
