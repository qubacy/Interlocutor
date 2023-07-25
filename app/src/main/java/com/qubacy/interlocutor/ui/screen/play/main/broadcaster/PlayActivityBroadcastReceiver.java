package com.qubacy.interlocutor.ui.screen.play.main.broadcaster;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.common.activity.broadcaster.ErrorHandlingBroadcastReceiver;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.main.broadcaster.error.PlayActivityBroadcastErrorEnum;

public class PlayActivityBroadcastReceiver extends ErrorHandlingBroadcastReceiver {
    public static final String C_IS_INCORRECT_PROP_NAME = "isIncorrect";

    protected PlayActivityBroadcastReceiver(
            final Context context,
            final PlayActivityBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static PlayActivityBroadcastReceiver getInstance(
            final Context context,
            final PlayActivityBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayActivityBroadcastReceiver(context, callback);
    }

    public static PlayActivityBroadcastReceiver start(
            final Context context,
            final PlayActivityBroadcastReceiverCallback callback)
    {
        PlayActivityBroadcastReceiver broadcastReceiver =
                PlayActivityBroadcastReceiver.getInstance(context, callback);

        IntentFilter intentFilter = broadcastReceiver.generateIntentFilter();

        LocalBroadcastManager.
                getInstance(context).
                registerReceiver(broadcastReceiver, intentFilter);

        return broadcastReceiver;
    }

    public static void stop(
            final Context context,
            final PlayActivityBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    public static void broadcastDisconnection(
            final Context context, final boolean isIncorrect)
    {
        Intent intent =
                new Intent(PlayActivityBroadcastCommand.DISCONNECTION.toString());

        intent.putExtra(C_IS_INCORRECT_PROP_NAME, isIncorrect);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = super.generateIntentFilter();

        for (final PlayActivityBroadcastCommand command :
                PlayActivityBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        return intentFilter;
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
        if (super.processBroadcast(context, intent)) return true;

        String action  = intent.getAction();
        PlayActivityBroadcastCommand command =
                PlayActivityBroadcastCommand.getCommandByCommandString(action);

        if (command == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            PlayActivityBroadcastErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                            PlayActivityBroadcastErrorEnum.INCORRECT_COMMAND.isCritical());

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
            final PlayActivityBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case DISCONNECTION:
                return processDisconnectionCommand(data);
        }

        Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                        m_context,
                        PlayActivityBroadcastErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                        PlayActivityBroadcastErrorEnum.INCORRECT_COMMAND.isCritical());

        return error;
    }

    private Error processDisconnectionCommand(final Intent data) {
        if (!data.hasExtra(C_IS_INCORRECT_PROP_NAME)) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            PlayActivityBroadcastErrorEnum.LACKING_DISCONNECTION_DATA.getResourceCode(),
                            PlayActivityBroadcastErrorEnum.LACKING_DISCONNECTION_DATA.isCritical());

            return error;
        }

        ((PlayActivityBroadcastReceiverCallback)m_callback).
                onDisconnectionOccurred(
                        data.getBooleanExtra(C_IS_INCORRECT_PROP_NAME, false));

        return null;
    }
}
