package com.qubacy.interlocutor.data.game.internal.service.launcher;

import android.content.Context;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.game.internal.service.GameService;

import java.io.Serializable;

public class GameServiceLauncherImpl extends GameServiceLauncher
    implements Serializable
{

    protected GameServiceLauncherImpl(
            final GameSessionProcessorFactory gameSessionProcessorFactory)
    {
        super(gameSessionProcessorFactory);
    }

    public static GameServiceLauncherImpl getInstance(
            final GameSessionProcessorFactory gameSessionProcessorFactory)
    {
        if (gameSessionProcessorFactory == null) return null;

        return new GameServiceLauncherImpl(gameSessionProcessorFactory);
    }

    @Override
    public void startService(@NonNull final Context context)
    {
        GameService.start(context, m_gameSessionProcessorFactory);
    }

    @Override
    public void stopService(@NonNull final Context context) {
        GameService.stop(context);
    }
}
