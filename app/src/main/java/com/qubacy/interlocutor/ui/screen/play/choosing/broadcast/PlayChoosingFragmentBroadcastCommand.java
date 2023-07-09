package com.qubacy.interlocutor.ui.screen.play.choosing.broadcast;

import androidx.annotation.NonNull;

public enum PlayChoosingFragmentBroadcastCommand {
    TIME_IS_OVER(
            PlayChoosingFragmentBroadcastReceiver.class.getName() + ".time_is_over");

    private final String m_commandString;

    private PlayChoosingFragmentBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static PlayChoosingFragmentBroadcastCommand getCommandByCommandString(
            final String commandString)
    {
        if (commandString == null) return null;

        for (final PlayChoosingFragmentBroadcastCommand command :
                PlayChoosingFragmentBroadcastCommand.values())
        {
            if (command.toString().equals(commandString))
                return command;
        }

        return null;
    }
}
