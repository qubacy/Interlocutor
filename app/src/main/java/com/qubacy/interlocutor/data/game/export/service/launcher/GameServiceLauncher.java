package com.qubacy.interlocutor.data.game.export.service.launcher;

import android.content.Context;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;

import java.io.Serializable;

public abstract class GameServiceLauncher implements Serializable {
    protected final GameSessionProcessorFactory m_gameSessionProcessorFactory;

    protected GameServiceLauncher(
            final GameSessionProcessorFactory gameSessionProcessorFactory)
    {
        m_gameSessionProcessorFactory = gameSessionProcessorFactory;
    }

    public abstract void startService(@NonNull final Context context);
    public abstract void stopService(@NonNull final Context context);
}
