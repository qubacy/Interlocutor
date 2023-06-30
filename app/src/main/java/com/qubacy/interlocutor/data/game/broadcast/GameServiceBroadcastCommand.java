package com.qubacy.interlocutor.data.game.broadcast;

import androidx.annotation.NonNull;

public enum GameServiceBroadcastCommand {
    START_SEARCHING(GameServiceBroadcastReceiver.class.getName() + ".START_SEARCHING"),
    STOP_SEARCHING(GameServiceBroadcastReceiver.class.getName() + ".STOP_SEARCHING"),
    SEND_MESSAGE(GameServiceBroadcastReceiver.class.getName() + ".SEND_MESSAGE"),
    CHOOSE_USERS(GameServiceBroadcastReceiver.class.getName() + ".CHOOSE_USERS"),
    LEAVE(GameServiceBroadcastReceiver.class.getName() + ".LEAVE");

    final private String m_commandString;

    private GameServiceBroadcastCommand(final String commandString) {
        m_commandString = commandString;
    }

    @NonNull
    public String toString() {
        return m_commandString;
    }

    public static GameServiceBroadcastCommand getCommandByCommandString(final String commandString) {
        if (commandString == null) return null;

        for (final GameServiceBroadcastCommand command : GameServiceBroadcastCommand.values()) {
            if (commandString.equals(command.m_commandString))
                return command;
        }

        return null;
    }
}
