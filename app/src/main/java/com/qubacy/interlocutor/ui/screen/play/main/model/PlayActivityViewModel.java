package com.qubacy.interlocutor.ui.screen.play.main.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;

public class PlayActivityViewModel extends ViewModel {
    private boolean m_isGameServiceLaunched = false;
    private GameServiceLauncher m_gameServiceLauncher = null;

    public boolean isGameServiceLaunched() {
        return m_isGameServiceLaunched;
    }

    public void setGameServiceLaunched(final boolean isGameServiceLaunched) {
        m_isGameServiceLaunched = isGameServiceLaunched;
    }

    public GameServiceLauncher getGameServiceLauncher() {
        return m_gameServiceLauncher;
    }

    public boolean setGameServiceLauncher(
            @NonNull final GameServiceLauncher gameServiceLauncher)
    {
        m_gameServiceLauncher = gameServiceLauncher;

        return true;
    }

    public boolean isInitialized() {
        return (m_gameServiceLauncher != null);
    }

    public boolean startServices(@NonNull final Context context) {
        if (m_isGameServiceLaunched)
            return true;

        m_isGameServiceLaunched = true;

        m_gameServiceLauncher.startService(context);

        return true;
    }
}
