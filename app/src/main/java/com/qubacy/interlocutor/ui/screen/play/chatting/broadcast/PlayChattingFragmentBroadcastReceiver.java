package com.qubacy.interlocutor.ui.screen.play.chatting.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.error.PlayChattingFragmentBroadcastErrorEnum;

import java.io.Serializable;

public class PlayChattingFragmentBroadcastReceiver extends BroadcastReceiver {
    public static final String C_MESSAGE_PROP_NAME = "message";

    private final Context m_context;
    private final PlayChattingFragmentBroadcastReceiverCallback m_callback;

    protected PlayChattingFragmentBroadcastReceiver(
            final Context context,
            final PlayChattingFragmentBroadcastReceiverCallback callback)
    {
        m_context = context;
        m_callback = callback;
    }

    public static PlayChattingFragmentBroadcastReceiver getInstance(
            final Context context,
            final PlayChattingFragmentBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayChattingFragmentBroadcastReceiver(context, callback);
    }

    public static PlayChattingFragmentBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final PlayChattingFragmentBroadcastReceiverCallback callback)
    {
        IntentFilter intentFilter = new IntentFilter();

        for (final PlayChattingFragmentBroadcastCommand command :
                PlayChattingFragmentBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        PlayChattingFragmentBroadcastReceiver playChattingFragmentBroadcastReceiver =
                new PlayChattingFragmentBroadcastReceiver(context, callback);

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

        m_callback.onMessageReceived(message);

        return null;
    }

    private Error processTimeIsOverCommand(final Intent data) {
        m_callback.onTimeIsOver();

        return null;
    }
}
