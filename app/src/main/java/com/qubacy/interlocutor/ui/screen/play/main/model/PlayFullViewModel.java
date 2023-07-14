package com.qubacy.interlocutor.ui.screen.play.main.model;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.chatting.model.PlayChattingViewModel;
import com.qubacy.interlocutor.ui.screen.play.choosing.model.PlayChoosingViewModel;
import com.qubacy.interlocutor.ui.screen.play.common.model.PlayViewModel;
import com.qubacy.interlocutor.ui.screen.play.results.model.PlayResultsViewModel;
import com.qubacy.interlocutor.ui.screen.play.searching.model.PlaySearchingViewModel;

import java.util.List;

public class PlayFullViewModel extends PlayViewModel
    implements
        PlaySearchingViewModel,
        PlayChattingViewModel,
        PlayChoosingViewModel,
        PlayResultsViewModel
{
    protected Profile m_profile = null;
    protected FoundGameData m_foundGameData = null;
    protected List<MatchedUserProfileData> m_userIdContactDataList = null;

    public PlayFullViewModel() {

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
        if (m_foundGameData == null) return null;

        return m_foundGameData.getChattingTopic();
    }

    @Override
    public long getChattingDuration() {
        if (m_foundGameData == null) return 0;

        return m_foundGameData.getChattingStageDuration();
    }

    @Override
    public long getChoosingDuration() {
        if (m_foundGameData == null) return 0;

        return m_foundGameData.getChoosingStageDuration();
    }

    @Override
    public List<ProfilePublic> getUserList() {
        if (m_foundGameData == null) return null;

        return m_foundGameData.getProfilePublicList();
    }

    @Override
    public ProfilePublic getProfileByIndex(final int index) {
        if (m_foundGameData == null) return null;

        return m_foundGameData.getProfilePublicList().get(index);
    }

    @Override
    public int getUserCount() {
        if (m_foundGameData == null) return 0;

        return m_foundGameData.getProfilePublicList().size();
    }

    @Override
    public boolean setUserIdContactDataList(
            final List<MatchedUserProfileData> userIdContactDataList)
    {
        if (userIdContactDataList == null) return false;

        m_userIdContactDataList = userIdContactDataList;

        return true;
    }

    @Override
    public MatchedUserProfileData getMatchedUserProfileDataByIndex(final int index) {
        if (m_userIdContactDataList == null) return null;

        return m_userIdContactDataList.get(index);
    }

    @Override
    public int getMatchedUserProfileDataCount() {
        if (m_userIdContactDataList == null) return 0;

        return m_userIdContactDataList.size();
    }
}
