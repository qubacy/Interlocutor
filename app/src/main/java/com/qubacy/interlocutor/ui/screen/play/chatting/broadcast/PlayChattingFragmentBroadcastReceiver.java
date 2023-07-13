package com.qubacy.interlocutor.ui.screen.play.chatting.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBase;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.error.PlayChattingFragmentBroadcastErrorEnum;

import java.io.Serializable;

public class PlayChattingFragmentBroadcastReceiver extends BroadcastReceiverBase {
    public static final String C_MESSAGE_PROP_NAME = "message";

    protected PlayChattingFragmentBroadcastReceiver(
            final Context context,
            final PlayChattingFragmentBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static PlayChattingFragmentBroadcastReceiver getInstance(
            final Context context,
            final PlayChattingFragmentBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayChattingFragmentBroadcastReceiver(context, callback);
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();

        for (final PlayChattingFragmentBroadcastCommand command :
                PlayChattingFragmentBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        return intentFilter;
    }

    public static PlayChattingFragmentBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final PlayChattingFragmentBroadcastReceiverCallback callback)
    {
        PlayChattingFragmentBroadcastReceiver playChattingFragmentBroadcastReceiver =
                new PlayChattingFragmentBroadcastReceiver(context, callback);

        IntentFilter intentFilter = playChattingFragmentBroadcastReceiver.generateIntentFilter();

        LocalBroadcastManager.
                getInstance(context).
                registerReceiver(playChattingFragmentBroadcastReceiver, intentFilter);

        return playChattingFragmentBroadcastReceiver;
    }

    public static void stop(
            @NonNull final Context context,
            @NonNull final PlayChattingFragmentBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.
                getInstance(context).
                unregisterReceiver(broadcastReceiver);
    }

    public static void broadcastMessageReceived(
            @NonNull final Context context,
            @NonNull final Message message)
    {
        Intent intent =
                new Intent(PlayChattingFragmentBroadcastCommand.MESSAGE_RECEIVED.toString());

        intent.putExtra(C_MESSAGE_PROP_NAME, message);

        LocalBroadcastManager.
                getInstance(context).
                sendBroadcast(intent);
    }

    public static void broadcastChattingPhaseIsOver(
            @NonNull final Context context)
    {
        Intent intent =
                new Intent(PlayChattingFragmentBroadcastCommand.TIME_IS_OVER.toString());

        LocalBroadcastManager.
                getInstance(context).
                sendBroadcast(intent);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        String action  = intent.getAction();
        PlayChattingFragmentBroadcastCommand command =
                PlayChattingFragmentBroadcastCommand.getCommandByCommandString(action);

        if (command == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                    PlayChattingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.isCritical());

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
            final PlayChattingFragmentBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case MESSAGE_RECEIVED: return processReceivedMessageCommand(data);
            case TIME_IS_OVER: return processTimeIsOverCommand(data);
        }

        Error error =
            ErrorUtility.getErrorByStringResourceCodeAndFlag(
                m_context,
                PlayChattingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.getResourceCode(),
                PlayChattingFragmentBroadcastErrorEnum.INCORRECT_COMMAND.isCritical());

        return error;
    }

    private Error processReceivedMessageCommand(final Intent data) {
        if (!data.hasExtra(C_MESSAGE_PROP_NAME)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentBroadcastErrorEnum.LACKING_MESSAGE_DATA.getResourceCode(),
                    PlayChattingFragmentBroadcastErrorEnum.LACKING_MESSAGE_DATA.isCritical());

            return error;
        }

        Serializable messageSerializable = data.getSerializableExtra(C_MESSAGE_PROP_NAME);

        if (!(messageSerializable instanceof Message)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentBroadcastErrorEnum.INCORRECT_MESSAGE_DATA.getResourceCode(),
                    PlayChattingFragmentBroadcastErrorEnum.INCORRECT_MESSAGE_DATA.isCritical());

            return error;
        }

        Message message = (Message) messageSerializable;

        ((PlayChattingFragmentBroadcastReceiverCallback)m_callback).onMessageReceived(message);

        return null;
    }

    private Error processTimeIsOverCommand(final Intent data) {
        ((PlayChattingFragmentBroadcastReceiverCallback)m_callback).onTimeIsOver();

        return null;
    }
}
