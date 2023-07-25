package com.qubacy.interlocutor.ui.screen.play.main.broadcaster;

import androidx.annotation.NonNull;

public enum PlayActivityBroadcastCommand {
    DISCONNECTION(
            PlayActivityBroadcastReceiver.class.getName() + ".disconnection");

    private final String m_commandString;

    private PlayActivityBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static PlayActivityBroadcastCommand getCommandByCommandString(
            final String commandString)
    {
        if (commandString == null) return null;

        for (final PlayActivityBroadcastCommand command :
                PlayActivityBroadcastCommand.values())
        {
            if (command.toString().equals(commandString))
                return command;
        }

        return null;
    }
}
