package com.qubacy.interlocutor.ui.common.activity.broadcaster;

import androidx.annotation.NonNull;

public enum ErrorHandlingBroadcastCommand {
    ERROR_OCCURRED(ErrorHandlingBroadcastReceiver.class.getName() + ".error_occurred");

    final private String m_commandString;

    private ErrorHandlingBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static ErrorHandlingBroadcastCommand getCommandByCommandString(final String commandString) {
        if (commandString == null) return null;

        for (final ErrorHandlingBroadcastCommand command : ErrorHandlingBroadcastCommand.values()) {
            if (command.m_commandString.equals(commandString))
                return command;
        }

        return null;
    }
}
