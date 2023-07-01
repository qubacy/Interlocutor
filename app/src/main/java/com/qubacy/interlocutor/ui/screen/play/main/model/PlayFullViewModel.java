package com.qubacy.interlocutor.ui.screen.play.main.model;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.ui.screen.play.model.PlayViewModel;
import com.qubacy.interlocutor.ui.screen.play.searching.model.PlaySearchingViewModel;

public class PlayFullViewModel extends PlayViewModel
    implements
        PlaySearchingViewModel
{
    protected Profile m_profile = null;
    protected FoundGameData m_foundGameData = null;

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
}
