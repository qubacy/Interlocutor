package com.qubacy.interlocutor.ui.screen.play.searching.broadcast;

import androidx.annotation.NonNull;

public enum PlaySearchingFragmentBroadcastCommand {
    SERVICE_READY(PlaySearchingFragmentBroadcastReceiver.class.getName() + ".service_ready"),
    GAME_FOUND(PlaySearchingFragmentBroadcastReceiver.class.getName() + ".game_found"),
    SEARCHING_STOPPED(PlaySearchingFragmentBroadcastReceiver.class.getName() + ".searching_stopped");

    private final String m_commandString;

    private PlaySearchingFragmentBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static PlaySearchingFragmentBroadcastCommand getCommandByCommandString(
            final String commandString)
    {
        if (commandString == null) return null;

        for (final PlaySearchingFragmentBroadcastCommand command :
                PlaySearchingFragmentBroadcastCommand.values())
        {
            if (command.toString().equals(commandString))
                return command;
        }

        return null;
    }
}
