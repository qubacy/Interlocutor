package com.qubacy.interlocutor.ui.screen.play.searching.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.data.game.export.service.broadcast.GameServiceBroadcastReceiver;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

public class PlaySearchingFragmentViewModel extends ViewModel {
    private boolean m_isSearchingLaunched = false;

    public boolean isSearchingLaunched() {
        return m_isSearchingLaunched;
    }

    public void setIsSearchingLaunched(final boolean isSearchingLaunched) {
        m_isSearchingLaunched = isSearchingLaunched;
    }

    public void processSearchingStart(
            @NonNull final Context context,
            @NonNull final Profile profile)
    {
        if (m_isSearchingLaunched) return;

        m_isSearchingLaunched = true;

        GameServiceBroadcastReceiver.broadcastStartGameSearching(context, profile);
    }

    public void processSearchingStop(
            @NonNull final Context context)
    {
        GameServiceBroadcastReceiver.broadcastStopGameSearching(context);
    }
}
