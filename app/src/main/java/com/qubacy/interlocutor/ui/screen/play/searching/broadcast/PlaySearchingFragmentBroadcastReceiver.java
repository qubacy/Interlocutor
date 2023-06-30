package com.qubacy.interlocutor.ui.screen.play.searching.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.struct.FoundGameData;
import com.qubacy.interlocutor.data.general.struct.error.Error;
import com.qubacy.interlocutor.data.general.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.searching.broadcast.error.PlaySearchingFragmentBroadcastReceiverErrorEnum;

import java.io.Serializable;

public class PlaySearchingFragmentBroadcastReceiver extends BroadcastReceiver {
    public static final String C_GAME_FOUND_DATA_PROP_NAME = "gameFoundData";

    private final Context m_context;
    private final PlaySearchingFragmentBroadcastReceiverCallback m_callback;

    protected PlaySearchingFragmentBroadcastReceiver(
            final Context context,
            final PlaySearchingFragmentBroadcastReceiverCallback callback)
    {
        super();

        m_context = context;
        m_callback = callback;
    }

    public static PlaySearchingFragmentBroadcastReceiver getInstance(
            final Context context,
            final PlaySearchingFragmentBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlaySearchingFragmentBroadcastReceiver(context, callback);
    }

    public static PlaySearchingFragmentBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final PlaySearchingFragmentBroadcastReceiverCallback callback)
    {
        IntentFilter intentFilter = new IntentFilter();

        for (final PlaySearchingFragmentBroadcastCommand command :
                PlaySearchingFragmentBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        PlaySearchingFragmentBroadcastReceiver playSearchingFragmentBroadcastReceiver =
                new PlaySearchingFragmentBroadcastReceiver(context, callback);

        LocalBroadcastManager.getInstance(context).registerReceiver(
                playSearchingFragmentBroadcastReceiver, intentFilter);

        return playSearchingFragmentBroadcastReceiver;
    }

    public static void stop(
            @NonNull final Context context,
            @NonNull final PlaySearchingFragmentBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        String action = intent.getAction();
        PlaySearchingFragmentBroadcastCommand command =
                PlaySearchingFragmentBroadcastCommand.getCommandByCommandString(action);

        if (command == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                    PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_COMMAND.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        Error commandProcessingError = processCommand(command, intent);

        if (commandProcessingError != null) {
            MainActivityBroadcastReceiver.broadcastError(m_context, commandProcessingError);

            return;
        }
    }

    private Error processCommand(
            final PlaySearchingFragmentBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case GAME_FOUND: return processGameFoundCommand(data);
            case SEARCHING_STOPPED: return processSearchingStoppedCommand(data);
        }

        Error error =
            ErrorUtility.getErrorByStringResourceCodeAndFlag(
                m_context,
                PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_COMMAND.isCritical());

        return error;
    }

    private Error processGameFoundCommand(final Intent data) {
        if (!data.hasExtra(C_GAME_FOUND_DATA_PROP_NAME)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlaySearchingFragmentBroadcastReceiverErrorEnum.LACKING_FOUND_GAME_DATA.getResourceCode(),
                    PlaySearchingFragmentBroadcastReceiverErrorEnum.LACKING_FOUND_GAME_DATA.isCritical());

            return error;
        }

        Serializable foundGameDataSerializable =
                data.getSerializableExtra(C_GAME_FOUND_DATA_PROP_NAME);

        if (!(foundGameDataSerializable instanceof FoundGameData)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_FOUND_GAME_DATA.getResourceCode(),
                    PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_FOUND_GAME_DATA.isCritical());

            return error;
        }

        FoundGameData foundGameData = (FoundGameData) foundGameDataSerializable;

        m_callback.onGameFound(foundGameData);

        return null;
    }

    private Error processSearchingStoppedCommand(final Intent data) {
        m_callback.onSearchingStopped();

        return null;
    }
}
