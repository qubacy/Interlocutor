package com.qubacy.interlocutor.data.game.export.service.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.service.broadcast.GameServiceBroadcastCommand;
import com.qubacy.interlocutor.data.game.internal.service.broadcast.GameServiceBroadcastReceiverCallback;
import com.qubacy.interlocutor.data.game.internal.service.broadcast.error.GameServiceBroadcastReceiverErrorEnum;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameServiceBroadcastReceiver extends BroadcastReceiver {
    public static final String C_PROFILE_PROP_NAME = "profile";
    public static final String C_MESSAGE_PROP_NAME = "message";
    public static final String C_CHOSEN_USER_ID_LIST_PROP_NAME = "chosenUserIdList";

    private final Context m_context;
    private final GameServiceBroadcastReceiverCallback m_callback;

    protected GameServiceBroadcastReceiver(
            final Context context,
            final GameServiceBroadcastReceiverCallback callback)
    {
        super();

        m_context = context;
        m_callback = callback;
    }

    public static GameServiceBroadcastReceiver getInstance(
            final Context context,
            final GameServiceBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new GameServiceBroadcastReceiver(context, callback);
    }

    public static GameServiceBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final GameServiceBroadcastReceiverCallback callback)
    {
        IntentFilter intentFilter = new IntentFilter();

        for (final GameServiceBroadcastCommand command : GameServiceBroadcastCommand.values())
            intentFilter.addAction(command.toString());

        GameServiceBroadcastReceiver gameServiceBroadcastReceiver =
                new GameServiceBroadcastReceiver(context, callback);

        LocalBroadcastManager.
                getInstance(context.getApplicationContext()).
                registerReceiver(gameServiceBroadcastReceiver, intentFilter);

        return gameServiceBroadcastReceiver;
    }

    public static void stop(
            @NonNull final Context context,
            @NonNull final GameServiceBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    public static void broadcastStartGameSearching(
            @NonNull final Context context,
            @NonNull final Profile profile)
    {
        Intent intent = new Intent(GameServiceBroadcastCommand.START_SEARCHING.toString());

        intent.putExtra(C_PROFILE_PROP_NAME, profile);

        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    public static void broadcastStopGameSearching(@NonNull final Context context) {
        Intent intent = new Intent(GameServiceBroadcastCommand.STOP_SEARCHING.toString());

        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    public static void broadcastSendMessage(
            @NonNull final Context context,
            @NonNull final Message message)
    {
        Intent intent = new Intent(GameServiceBroadcastCommand.SEND_MESSAGE.toString());

        intent.putExtra(C_MESSAGE_PROP_NAME, message);

        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    public static void broadcastChooseUsers(
            @NonNull final Context context,
            @NonNull final List<Integer> chosenUserIdList)
    {
        Intent intent = new Intent(GameServiceBroadcastCommand.CHOOSE_USERS.toString());

        intent.putIntegerArrayListExtra(
                C_CHOSEN_USER_ID_LIST_PROP_NAME, (ArrayList<Integer>) chosenUserIdList);

        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        String action = intent.getAction();
        GameServiceBroadcastCommand command =
                GameServiceBroadcastCommand.getCommandByCommandString(action);

        if (command == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            context,
                            GameServiceBroadcastReceiverErrorEnum.
                                    INCORRECT_COMMAND.
                                    getResourceCode(),
                            GameServiceBroadcastReceiverErrorEnum.
                                    INCORRECT_COMMAND.
                                    isCritical());

            MainActivityBroadcastReceiver.
                    broadcastError(
                            context.getApplicationContext(),
                            error);

            return;
        }

        Error commandProcessingError = processCommand(command, intent);

        if (commandProcessingError != null) {
            MainActivityBroadcastReceiver.
                    broadcastError(context.getApplicationContext(), commandProcessingError);

            return;
        }
    }

    private Error processCommand(
            final GameServiceBroadcastCommand command,
            final Intent data)
    {
        switch (command) {
            case START_SEARCHING: return startSearching(data);
            case STOP_SEARCHING: return stopSearching(data);
            case SEND_MESSAGE: return sendMessage(data);
            case CHOOSE_USERS: return chooseUsers(data);
        }

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameServiceBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.getResourceCode(),
                    GameServiceBroadcastReceiverErrorEnum.UNKNOWN_COMMAND_TYPE.isCritical());

        return error;
    }

    private Error startSearching(final Intent data) {
        if (!data.hasExtra(C_PROFILE_PROP_NAME)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameServiceBroadcastReceiverErrorEnum.LACKING_PROFILE_DATA.getResourceCode(),
                        GameServiceBroadcastReceiverErrorEnum.LACKING_PROFILE_DATA.isCritical());

            return error;
        }

        Serializable profileSerializable = data.getSerializableExtra(C_PROFILE_PROP_NAME);

        if (!(profileSerializable instanceof Profile)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameServiceBroadcastReceiverErrorEnum.INCORRECT_PROFILE_DATA.getResourceCode(),
                        GameServiceBroadcastReceiverErrorEnum.INCORRECT_PROFILE_DATA.isCritical());

            return error;
        }

        Profile profile = (Profile) profileSerializable;

        m_callback.onStartSearchingRequested(profile);

        return null;
    }

    private Error stopSearching(final Intent data) {
        m_callback.onStopSearchingRequested();

        return null;
    }

    private Error sendMessage(final Intent data) {
        if (!data.hasExtra(C_MESSAGE_PROP_NAME)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameServiceBroadcastReceiverErrorEnum.LACKING_MESSAGE_DATA.getResourceCode(),
                        GameServiceBroadcastReceiverErrorEnum.LACKING_MESSAGE_DATA.isCritical());

            return error;
        }

        Serializable messageSerializable = data.getSerializableExtra(C_MESSAGE_PROP_NAME);

        if (!(messageSerializable instanceof Message)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameServiceBroadcastReceiverErrorEnum.INCORRECT_MESSAGE_DATA.getResourceCode(),
                        GameServiceBroadcastReceiverErrorEnum.INCORRECT_MESSAGE_DATA.isCritical());

            return error;
        }

        Message message = (Message) messageSerializable;

        m_callback.onMessageSendingRequested(message);

        return null;
    }

    private Error chooseUsers(final Intent data) {
        if (!data.hasExtra(C_CHOSEN_USER_ID_LIST_PROP_NAME)) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameServiceBroadcastReceiverErrorEnum.LACKING_CHOSEN_USERS_DATA.getResourceCode(),
                        GameServiceBroadcastReceiverErrorEnum.LACKING_CHOSEN_USERS_DATA.isCritical());

            return error;
        }

        List<Integer> chosenUserIdList =
                data.getIntegerArrayListExtra(C_CHOSEN_USER_ID_LIST_PROP_NAME);

        if (chosenUserIdList.isEmpty()) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameServiceBroadcastReceiverErrorEnum.LACKING_CHOSEN_USERS_DATA.getResourceCode(),
                        GameServiceBroadcastReceiverErrorEnum.LACKING_CHOSEN_USERS_DATA.isCritical());

            return error;
        }

        m_callback.onChooseUsersRequested(chosenUserIdList);

        return null;
    }
}
