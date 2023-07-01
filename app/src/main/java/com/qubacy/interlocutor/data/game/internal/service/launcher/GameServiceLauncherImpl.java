package com.qubacy.interlocutor.data.game.internal.service.launcher;

import android.content.Context;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.game.internal.service.GameService;

import java.io.Serializable;

public class GameServiceLauncherImpl extends GameServiceLauncher
    implements Serializable
{

    protected GameServiceLauncherImpl(
            final GameSessionProcessor gameSessionProcessor)
    {
        super(gameSessionProcessor);
    }

    public static GameServiceLauncherImpl getInstance(
            final GameSessionProcessor gameSessionProcessor)
    {
        if (gameSessionProcessor == null) return null;

        return new GameServiceLauncherImpl(gameSessionProcessor);
    }

    @Override
    public void startService(@NonNull final Context context)
    {
        GameService.start(context, m_gameSessionProcessor);
    }

    @Override
    public void stopService(@NonNull final Context context) {
        GameService.stop(context);
    }
}
