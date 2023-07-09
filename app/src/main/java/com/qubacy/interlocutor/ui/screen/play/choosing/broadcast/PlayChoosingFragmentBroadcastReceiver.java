package com.qubacy.interlocutor.ui.screen.play.choosing.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.choosing.broadcast.error.PlayChoosingFragmentBroadcastErrorEnum;

public class PlayChoosingFragmentBroadcastReceiver extends BroadcastReceiver {
    private final Context m_context;
    private final PlayChoosingFragmentBroadcastReceiverCallback m_callback;

    protected PlayChoosingFragmentBroadcastReceiver(
            final Context context,
            final PlayChoosingFragmentBroadcastReceiverCallback callback)
    {
        m_context = context;
        m_callback = callback;
    }

    public static PlayChoosingFragmentBroadcastReceiver getInstance(
            final Context context,
            final PlayChoosingFragmentBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayChoosingFragmentBroadcastReceiver(context, callback);
    }

    public static PlayChoosingFragmentBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final PlayChoosingFragmentBroadcastReceiverCallback callback)
    {
        IntentFilter intentFilter = new IntentFilter();

        for (final PlayChoosingFragmentBroadcastCommand command :
                PlayChoosingFragmentBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        PlayChoosingFragmentBroadcastReceiver playChoosingFragmentBroadcastReceiver =
                new PlayChoosingFragmentBroadcastReceiver(context, callback);

        LocalBroadcastManager.
                getInstance(context).
                registerReceiver(playChoosingFragmentBroadcastReceiver, intentFilter);

        return playChoosingFragmentBroadcastReceiver;
    }

    public static void stop(
            @NonNull final Context context,
            @NonNull final PlayChoosingFragmentBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.
                getInstance(context).
                unregisterReceiver(broadcastReceiver);
    }

    public static void broadcastChoosingPhaseIsOver(
            @NonNull final Context context)
    {
        Intent intent = new Intent(PlayChoosingFragmentBroadcastCommand.TIME_IS_OVER.toString());

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        String action  = intent.getAction();
        PlayChoosingFragmentBroadcastCommand command =
                PlayChoosingFragmentBroadcastCommand.getCommandByCommandString(action);

        if (command == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            PlayChoosingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                            PlayChoosingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.isCritical());

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
            final PlayChoosingFragmentBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case TIME_IS_OVER: return processTimeIsOverCommand(data);
        }

        Error error =
            ErrorUtility.getErrorByStringResourceCodeAndFlag(
                m_context,
                PlayChoosingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                PlayChoosingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.isCritical());

        return error;
    }

    private Error processTimeIsOverCommand(final Intent data) {
        m_callback.onTimeIsOver();

        return null;
    }
}
