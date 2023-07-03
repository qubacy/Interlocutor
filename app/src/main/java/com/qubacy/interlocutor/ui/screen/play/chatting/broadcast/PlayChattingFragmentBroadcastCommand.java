package com.qubacy.interlocutor.ui.screen.play.chatting.broadcast;

import androidx.annotation.NonNull;

public enum PlayChattingFragmentBroadcastCommand {
    MESSAGE_RECEIVED(
            PlayChattingFragmentBroadcastReceiver.class.getName() + ".message_received"),
    TIME_IS_OVER(
            PlayChattingFragmentBroadcastReceiver.class.getName() + ".time_is_over");

    private final String m_commandString;

    private PlayChattingFragmentBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static PlayChattingFragmentBroadcastCommand getCommandByCommandString(
            final String commandString)
    {
        if (commandString == null) return null;

        for (final PlayChattingFragmentBroadcastCommand command :
                PlayChattingFragmentBroadcastCommand.values())
        {
            if (command.toString().equals(commandString))
                return command;
        }

        return null;
    }
}
