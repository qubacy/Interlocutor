package com.qubacy.interlocutor.ui.screen.play.searching.model;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

public interface PlaySearchingViewModel {
    public Profile getProfile();
    public boolean setFoundGameData(@NonNull final FoundGameData foundGameData);
}
