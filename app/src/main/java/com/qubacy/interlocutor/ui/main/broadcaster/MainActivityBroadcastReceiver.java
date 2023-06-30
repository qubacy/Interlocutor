package com.qubacy.interlocutor.ui.main.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.general.struct.error.Error;
import com.qubacy.interlocutor.data.general.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.error.MainActivityBroadcastReceiverErrorEnum;

import java.io.Serializable;

public class MainActivityBroadcastReceiver extends BroadcastReceiver {
    public static final String C_ERROR_PROP_NAME = "error";

    private final Context m_context;
    private final MainActivityBroadcastReceiverCallback m_callback;

    protected MainActivityBroadcastReceiver(
            final Context context,
            final MainActivityBroadcastReceiverCallback callback)
    {
        super();

        m_context = context;
        m_callback = callback;
    }

    public static MainActivityBroadcastReceiver getInstance(
            final Context context,
            final MainActivityBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new MainActivityBroadcastReceiver(context, callback);
    }

    public static MainActivityBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final MainActivityBroadcastReceiverCallback callback)
    {
        IntentFilter intentFilter = new IntentFilter();

        for (final MainActivityBroadcastCommand command : MainActivityBroadcastCommand.values())
            intentFilter.addAction(command.toString());

        MainActivityBroadcastReceiver mainActivityBroadcastReceiver =
                new MainActivityBroadcastReceiver(context, callback);

        LocalBroadcastManager.
                getInstance(context).
                registerReceiver(mainActivityBroadcastReceiver, intentFilter);

        return mainActivityBroadcastReceiver;
    }

    public static void stop(
            @NonNull final Context context,
            @NonNull final MainActivityBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    public static void broadcastError(
            @NonNull final Context context,
            @NonNull final Error error)
    {
        Intent errorIntent =
                new Intent(MainActivityBroadcastCommand.ERROR_OCCURRED.toString());

        errorIntent.putExtra(MainActivityBroadcastReceiver.C_ERROR_PROP_NAME, error);

        LocalBroadcastManager.
                getInstance(context.getApplicationContext()).
                sendBroadcast(errorIntent);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        if (intent == null) return;

        MainActivityBroadcastCommand command =
                MainActivityBroadcastCommand.getCommandByCommandString(intent.getAction());

        if (command == null) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        MainActivityBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.getResourceCode(),
                        MainActivityBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.isCritical());

            m_callback.onErrorOccurred(error);

            return;
        }

        Error error = processCommand(command, intent);

        if (error != null) {
            m_callback.onErrorOccurred(error);

            return;
        }
    }

    private Error processCommand(
            final MainActivityBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case ERROR_OCCURRED: return processErrorOccurredCommand(data);
        }

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    m_context,
                    MainActivityBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.getResourceCode(),
                    MainActivityBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.isCritical());

        return error;
    }

    private Error processErrorOccurredCommand(final Intent data) {
        if (!(data.hasExtra(C_ERROR_PROP_NAME))) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        MainActivityBroadcastReceiverErrorEnum.LACKING_ERROR_DATA.getResourceCode(),
                        MainActivityBroadcastReceiverErrorEnum.LACKING_ERROR_DATA.isCritical());

            return error;
        }

        Serializable errorSerializable = data.getSerializableExtra(C_ERROR_PROP_NAME);

        if (!(errorSerializable instanceof Error)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        MainActivityBroadcastReceiverErrorEnum.INCORRECT_ERROR_DATA.getResourceCode(),
                        MainActivityBroadcastReceiverErrorEnum.INCORRECT_ERROR_DATA.isCritical());

            return error;
        }

        Error error = (Error) errorSerializable;

        m_callback.onErrorOccurred(error);

        return null;
    }
}
