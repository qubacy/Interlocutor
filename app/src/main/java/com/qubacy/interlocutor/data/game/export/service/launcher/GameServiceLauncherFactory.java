package com.qubacy.interlocutor.data.game.export.service.launcher;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactoryGenerator;
import com.qubacy.interlocutor.data.game.internal.service.launcher.GameServiceLauncherImpl;

public class GameServiceLauncherFactory {
    protected GameServiceLauncherFactory() {

    }

    public static GameServiceLauncherFactory getInstance() {
        return new GameServiceLauncherFactory();
    }

    public GameServiceLauncher generateGameServiceLauncher() {
        GameSessionProcessorFactoryGenerator gameSessionProcessorFactoryGenerator =
                GameSessionProcessorFactoryGenerator.getInstance();

        if (gameSessionProcessorFactoryGenerator == null)
            return null;

        GameSessionProcessorFactory gameSessionProcessorFactory =
                gameSessionProcessorFactoryGenerator.generateGameSessionProcessor();

        return GameServiceLauncherImpl.getInstance(gameSessionProcessorFactory);
    }
}
