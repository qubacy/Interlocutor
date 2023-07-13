package com.qubacy.interlocutor.ui.main.broadcaster;

import androidx.annotation.NonNull;

public enum MainActivityBroadcastCommand {
    ERROR_OCCURRED(MainActivityBroadcastReceiver.class.getName() + ".error_occurred"),
    ERROR_PROCESSED(MainActivityBroadcastReceiver.class.getName() + ".shutdown_app");

    final private String m_commandString;

    private MainActivityBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static MainActivityBroadcastCommand getCommandByCommandString(final String commandString) {
        if (commandString == null) return null;

        for (final MainActivityBroadcastCommand command : MainActivityBroadcastCommand.values()) {
            if (command.m_commandString.equals(commandString))
                return command;
        }

        return null;
    }
}
