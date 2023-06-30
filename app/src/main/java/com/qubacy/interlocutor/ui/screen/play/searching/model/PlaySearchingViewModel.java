package com.qubacy.interlocutor.ui.screen.play.searching.model;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.struct.FoundGameData;
import com.qubacy.interlocutor.data.general.struct.profile.local.Profile;

public interface PlaySearchingViewModel {
    public Profile getProfile();
    public boolean setFoundGameData(@NonNull final FoundGameData foundGameData);
}
