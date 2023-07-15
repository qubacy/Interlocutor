package com.qubacy.interlocutor.ui.common.activity.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.common.activity.broadcaster.error.ErrorHandlingBroadcastReceiverErrorEnum;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBase;

import java.io.Serializable;

public abstract class ErrorHandlingBroadcastReceiver extends BroadcastReceiverBase {
    public static final String C_ERROR_PROP_NAME = "error";

    protected ErrorHandlingBroadcastReceiver(
            final Context context,
            final ErrorHandlingBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static void broadcastError(
            @NonNull final Context context,
            @NonNull final Error error)
    {
        Intent errorIntent =
                new Intent(ErrorHandlingBroadcastCommand.ERROR_OCCURRED.toString());

        errorIntent.putExtra(ErrorHandlingBroadcastReceiver.C_ERROR_PROP_NAME, error);

        LocalBroadcastManager.
                getInstance(context.getApplicationContext()).
                sendBroadcast(errorIntent);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        String action = intent.getAction();
        ErrorHandlingBroadcastCommand command =
                ErrorHandlingBroadcastCommand.getCommandByCommandString(action);

        if (command == null) return;

        Error processingError = processCommand(command, intent);

        if (processingError != null) {
            ((ErrorHandlingBroadcastReceiverCallback)m_callback).onErrorOccurred(processingError);

            return;
        }
    }

    protected Error processCommand(
            final ErrorHandlingBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case ERROR_OCCURRED: return processErrorOccurredCommand(data);
        }

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    m_context,
                    ErrorHandlingBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.getResourceCode(),
                    ErrorHandlingBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.isCritical());

        return error;
    }

    protected Error processErrorOccurredCommand(final Intent data) {
        if (!(data.hasExtra(C_ERROR_PROP_NAME))) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        ErrorHandlingBroadcastReceiverErrorEnum.LACKING_ERROR_DATA.getResourceCode(),
                        ErrorHandlingBroadcastReceiverErrorEnum.LACKING_ERROR_DATA.isCritical());

            return error;
        }

        Serializable errorSerializable = data.getSerializableExtra(C_ERROR_PROP_NAME);

        if (!(errorSerializable instanceof Error)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        ErrorHandlingBroadcastReceiverErrorEnum.INCORRECT_ERROR_DATA.getResourceCode(),
                        ErrorHandlingBroadcastReceiverErrorEnum.INCORRECT_ERROR_DATA.isCritical());

            return error;
        }

        Error error = (Error) errorSerializable;

        ((ErrorHandlingBroadcastReceiverCallback)m_callback).onErrorOccurred(error);

        return null;
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();

        for (final ErrorHandlingBroadcastCommand command :
                ErrorHandlingBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        return intentFilter;
    }
}
