package com.qubacy.interlocutor.ui.screen.play.searching.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBase;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.searching.broadcast.error.PlaySearchingFragmentBroadcastReceiverErrorEnum;

import java.io.Serializable;

public class PlaySearchingFragmentBroadcastReceiver extends BroadcastReceiverBase {
    public static final String C_GAME_FOUND_DATA_PROP_NAME = "gameFoundData";

    protected PlaySearchingFragmentBroadcastReceiver(
            final Context context,
            final PlaySearchingFragmentBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static PlaySearchingFragmentBroadcastReceiver getInstance(
            final Context context,
            final PlaySearchingFragmentBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlaySearchingFragmentBroadcastReceiver(context, callback);
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();

        for (final PlaySearchingFragmentBroadcastCommand command :
                PlaySearchingFragmentBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        return intentFilter;
    }

    public static PlaySearchingFragmentBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final PlaySearchingFragmentBroadcastReceiverCallback callback)
    {
        PlaySearchingFragmentBroadcastReceiver playSearchingFragmentBroadcastReceiver =
                new PlaySearchingFragmentBroadcastReceiver(context, callback);

        IntentFilter intentFilter =
                playSearchingFragmentBroadcastReceiver.generateIntentFilter();

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

    public static void broadcastServiceReady(
            @NonNull final Context context)
    {
        Intent intent =
                new Intent(PlaySearchingFragmentBroadcastCommand.SERVICE_READY.toString());

        LocalBroadcastManager.
                getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    public static void broadcastGameFound(
            @NonNull final FoundGameData foundGameData,
            @NonNull final Context context)
    {
        Intent intent = new Intent(PlaySearchingFragmentBroadcastCommand.GAME_FOUND.toString());

        intent.putExtra(
                PlaySearchingFragmentBroadcastReceiver.C_GAME_FOUND_DATA_PROP_NAME,
                foundGameData);

        LocalBroadcastManager.
                getInstance(context.getApplicationContext()).
                sendBroadcast(intent);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        if (!processBroadcast(context, intent)) return;
    }

    @Override
    protected boolean processBroadcast(Context context, Intent intent) {
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

            return false;
        }

        Error commandProcessingError = processCommand(command, intent);

        if (commandProcessingError != null) {
            MainActivityBroadcastReceiver.broadcastError(m_context, commandProcessingError);

            return false;
        }

        return true;
    }

    private Error processCommand(
            final PlaySearchingFragmentBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case SERVICE_READY: return processServiceReadyCommand(data);
            case GAME_FOUND: return processGameFoundCommand(data);
        }

        Error error =
            ErrorUtility.getErrorByStringResourceCodeAndFlag(
                m_context,
                PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                PlaySearchingFragmentBroadcastReceiverErrorEnum.INCORRECT_COMMAND.isCritical());

        return error;
    }

    private Error processServiceReadyCommand(final Intent data) {
        ((PlaySearchingFragmentBroadcastReceiverCallback)m_callback).onServiceReady();

        return null;
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

        ((PlaySearchingFragmentBroadcastReceiverCallback)m_callback).onGameFound(foundGameData);

        return null;
    }
}
