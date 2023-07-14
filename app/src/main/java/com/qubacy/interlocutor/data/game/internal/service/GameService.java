package com.qubacy.interlocutor.data.game.internal.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.service.broadcast.GameServiceBroadcastReceiver;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.service.broadcast.GameServiceBroadcastReceiverCallback;
import com.qubacy.interlocutor.data.game.internal.service.error.GameServiceErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessorCallback;
import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameDataMapper;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublicDataMapper;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.PlayChattingFragmentBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.choosing.broadcast.PlayChoosingFragmentBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.searching.broadcast.PlaySearchingFragmentBroadcastCommand;
import com.qubacy.interlocutor.ui.screen.play.searching.broadcast.PlaySearchingFragmentBroadcastReceiver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameService extends Service
    implements
        GameSessionProcessorCallback,
        GameServiceBroadcastReceiverCallback
{
    public static final String C_GAME_SESSION_PROCESSOR_PROP_NAME = "gameSessionProcessor";

    private GameServiceBroadcastReceiver m_gameSessionBroadcastReceiver = null;
    private GameSessionProcessor m_gameSessionProcessor = null;

    public static boolean start(
            @NonNull final Context context,
            @NonNull final GameSessionProcessor gameSessionProcessor)
    {
        Intent intent = new Intent(context, GameService.class);

        intent.putExtra(C_GAME_SESSION_PROCESSOR_PROP_NAME, gameSessionProcessor);

        context.startService(intent);

        return true;
    }

    public static boolean stop(@NonNull final Context context) {
        Intent intent = new Intent(context, GameService.class);

        context.stopService(intent);

        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GameServiceBroadcastReceiver gameSessionBroadcastReceiver =
                GameServiceBroadcastReceiver.start(this, this);

        if (gameSessionBroadcastReceiver == null) {
            Error error =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        this,
                        GameServiceErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.getResourceCode(),
                        GameServiceErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }

        m_gameSessionBroadcastReceiver = gameSessionBroadcastReceiver;

        processServiceReady();
    }

    private void processServiceReady() {
        PlaySearchingFragmentBroadcastReceiver.broadcastServiceReady(this);
    }

    @Override
    public void onDestroy() {
        if (m_gameSessionProcessor != null)
            m_gameSessionProcessor.stop();

        GameServiceBroadcastReceiver.stop(this, m_gameSessionBroadcastReceiver);

        super.onDestroy();
    }

    @Override
    public int onStartCommand(
            final Intent intent,
            final int flags,
            final int startId)
    {
        if (intent == null) return START_NOT_STICKY;
        if (!intent.hasExtra(C_GAME_SESSION_PROCESSOR_PROP_NAME))
            return START_NOT_STICKY;

        Serializable gameSessionProcessorSerializable =
                intent.getSerializableExtra(C_GAME_SESSION_PROCESSOR_PROP_NAME);

        if (!(gameSessionProcessorSerializable instanceof GameSessionProcessor)) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            this,
                            GameServiceErrorEnum.LACKING_GAME_PROCESSOR.getResourceCode(),
                            GameServiceErrorEnum.LACKING_GAME_PROCESSOR.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return START_NOT_STICKY;
        }

        m_gameSessionProcessor = (GameSessionProcessor) gameSessionProcessorSerializable;

        m_gameSessionProcessor.launch(this, this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    // GameSessionProcessor:

    @Override
    public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {
        // mb some actions...

        RemoteProfilePublicDataMapper remoteProfilePublicDataMapper =
                RemoteProfilePublicDataMapper.getInstance();

        if (remoteProfilePublicDataMapper == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.REMOTE_PROFILE_DATA_MAPPER_CREATION_FAILED.getResourceCode(),
                    GameServiceErrorEnum.REMOTE_PROFILE_DATA_MAPPER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }

        RemoteFoundGameDataMapper remoteFoundGameDataMapper =
                RemoteFoundGameDataMapper.getInstance(remoteProfilePublicDataMapper);

        if (remoteFoundGameDataMapper == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.REMOTE_FOUND_GAME_DATA_MAPPER_CREATION_FAILED.getResourceCode(),
                    GameServiceErrorEnum.REMOTE_FOUND_GAME_DATA_MAPPER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }

        FoundGameData mappedFoundGameData = remoteFoundGameDataMapper.map(foundGameData);

        if (mappedFoundGameData == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.REMOTE_FOUND_GAME_DATA_MAPPING_FAILED.getResourceCode(),
                    GameServiceErrorEnum.REMOTE_FOUND_GAME_DATA_MAPPING_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }

        PlaySearchingFragmentBroadcastReceiver.broadcastGameFound(
                mappedFoundGameData, this);
    }

    @Override
    public void gameSearchingAborted() {
        m_gameSessionProcessor.stopSearching();

        Intent intent =
                new Intent(PlaySearchingFragmentBroadcastCommand.SEARCHING_STOPPED.toString());

        LocalBroadcastManager.
                getInstance(getApplicationContext()).
                sendBroadcast(intent);
    }

    @Override
    public void errorOccurred(@NonNull final Error error) {
        MainActivityBroadcastReceiver.broadcastError(this, error);
    }

    @Override
    public void messageSent() {
        // is it necessary?
    }

    @Override
    public void messageReceived(@NonNull final RemoteMessage message) {
        PlayChattingFragmentBroadcastReceiver.broadcastMessageReceived(this, message);
    }

    @Override
    public void onChattingPhaseIsOver() {
        PlayChattingFragmentBroadcastReceiver.broadcastChattingPhaseIsOver(this);
    }

    @Override
    public void onChoosingPhaseIsOver(
            @NonNull final ArrayList<MatchedUserProfileData> userProfileContactDataList)
    {
        PlayChoosingFragmentBroadcastReceiver.broadcastChoosingPhaseIsOver(
                this, userProfileContactDataList);
    }

    // BroadcastReceiver:

    @Override
    public void onStartSearchingRequested(@NonNull final Profile profile) {
        if (m_gameSessionProcessor.startSearching(profile)) return;

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.STARTING_SEARCHING_FAILED.getResourceCode(),
                    GameServiceErrorEnum.STARTING_SEARCHING_FAILED.isCritical());

        MainActivityBroadcastReceiver.broadcastError(this, error);
    }

    @Override
    public void onStopSearchingRequested() {
        if (m_gameSessionProcessor.stopSearching()) return;

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.STOPPING_SEARCHING_FAILED.getResourceCode(),
                    GameServiceErrorEnum.STOPPING_SEARCHING_FAILED.isCritical());

        MainActivityBroadcastReceiver.broadcastError(this, error);
    }

    @Override
    public void onMessageSendingRequested(@NonNull final Message message) {
        if (m_gameSessionProcessor.sendMessage(message)) return;

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.SENDING_MESSAGE_FAILED.getResourceCode(),
                    GameServiceErrorEnum.SENDING_MESSAGE_FAILED.isCritical());

        MainActivityBroadcastReceiver.broadcastError(this, error);
    }

    @Override
    public void onChooseUsersRequested(
            @NonNull final List<Integer> chosenUserIdList)
    {
        if (m_gameSessionProcessor.chooseUsers(chosenUserIdList)) return;

        Error error =
            ErrorUtility.
                getErrorByStringResourceCodeAndFlag(
                    this,
                    GameServiceErrorEnum.CHOOSING_USERS_FAILED.getResourceCode(),
                    GameServiceErrorEnum.CHOOSING_USERS_FAILED.isCritical());

        MainActivityBroadcastReceiver.broadcastError(this, error);
    }

    @Override
    public void onLeaveRequested() {

    }

//    private void runOnMainThread(final Runnable function) {
//        new Handler(getMainLooper()).post(function);
//    }
}
