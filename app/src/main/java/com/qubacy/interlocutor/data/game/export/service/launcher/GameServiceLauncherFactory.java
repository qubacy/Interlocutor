package com.qubacy.interlocutor.data.game.export.service.launcher;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;
import com.qubacy.interlocutor.data.game.internal.service.launcher.GameServiceLauncherImpl;

public class GameServiceLauncherFactory {
    protected GameServiceLauncherFactory() {

    }

    public static GameServiceLauncherFactory getInstance() {
        return new GameServiceLauncherFactory();
    }

    public GameServiceLauncher generateGameServiceLauncher() {
        GameSessionProcessorFactory gameSessionProcessorFactory =
                GameSessionProcessorFactory.getInstance();

        if (gameSessionProcessorFactory == null) return null;

        GameSessionProcessor gameSessionProcessor =
                gameSessionProcessorFactory.generateGameSessionProcessor();

        return GameServiceLauncherImpl.getInstance(gameSessionProcessor);
    }
}
