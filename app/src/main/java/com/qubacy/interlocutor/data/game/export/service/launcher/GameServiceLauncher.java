package com.qubacy.interlocutor.data.game.export.service.launcher;

import android.content.Context;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;

import java.io.Serializable;

public abstract class GameServiceLauncher implements Serializable {
    protected final GameSessionProcessor m_gameSessionProcessor;

    protected GameServiceLauncher(
            final GameSessionProcessor gameSessionProcessor)
    {
        m_gameSessionProcessor = gameSessionProcessor;
    }

    public abstract void startService(@NonNull final Context context);
    public abstract void stopService(@NonNull final Context context);
}
