package com.qubacy.interlocutor.ui.screen.play.choosing.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBase;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.choosing.broadcast.error.PlayChoosingFragmentBroadcastErrorEnum;

import java.util.ArrayList;

public class PlayChoosingFragmentBroadcastReceiver extends BroadcastReceiverBase {
    public static final String C_USER_ID_CONTACT_PAIR_LIST_PROP_NAME = "userIdContactPairList";

    protected PlayChoosingFragmentBroadcastReceiver(
            final Context context,
            final PlayChoosingFragmentBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static PlayChoosingFragmentBroadcastReceiver getInstance(
            final Context context,
            final PlayChoosingFragmentBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayChoosingFragmentBroadcastReceiver(context, callback);
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();

        for (final PlayChoosingFragmentBroadcastCommand command :
                PlayChoosingFragmentBroadcastCommand.values())
        {
            intentFilter.addAction(command.toString());
        }

        return intentFilter;
    }

    public static PlayChoosingFragmentBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final PlayChoosingFragmentBroadcastReceiverCallback callback)
    {
        PlayChoosingFragmentBroadcastReceiver playChoosingFragmentBroadcastReceiver =
                new PlayChoosingFragmentBroadcastReceiver(context, callback);

        IntentFilter intentFilter = playChoosingFragmentBroadcastReceiver.generateIntentFilter();

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
            @NonNull final Context context,
            @NonNull final ArrayList<MatchedUserProfileData> userIdContactDataList)
    {
        Intent intent = new Intent(PlayChoosingFragmentBroadcastCommand.TIME_IS_OVER.toString());

        intent.putParcelableArrayListExtra(
                C_USER_ID_CONTACT_PAIR_LIST_PROP_NAME, userIdContactDataList);

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
        if (!data.hasExtra(C_USER_ID_CONTACT_PAIR_LIST_PROP_NAME)) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            PlayChoosingFragmentBroadcastErrorEnum.LACKING_USER_ID_CONTACT_DATA_LIST.
                                    getResourceCode(),
                            PlayChoosingFragmentBroadcastErrorEnum.LACKING_USER_ID_CONTACT_DATA_LIST.
                                    isCritical());

            return error;
        }

        ArrayList<MatchedUserProfileData> userIdContactDataList =
                data.getParcelableArrayListExtra(C_USER_ID_CONTACT_PAIR_LIST_PROP_NAME);

        if (userIdContactDataList == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            PlayChoosingFragmentBroadcastErrorEnum.INVALID_USER_ID_CONTACT_DATA_LIST.
                                    getResourceCode(),
                            PlayChoosingFragmentBroadcastErrorEnum.INVALID_USER_ID_CONTACT_DATA_LIST.
                                    isCritical());

            return error;
        }

        ((PlayChoosingFragmentBroadcastReceiverCallback)m_callback).
                onTimeIsOver(userIdContactDataList);

        return null;
    }
}
