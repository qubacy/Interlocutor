package com.qubacy.interlocutor.data.game.internal.processor.implfake;

import android.util.Log;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.internal.processor.error.GameSessionProcessorErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.state.GameSessionImplFakeStateChatting;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.state.GameSessionImplFakeStateSearching;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSessionProcessorImplFake extends GameSessionProcessor
        implements Serializable
{
    private static final long C_SEARCHING_TIME_MILLISECONDS = 5000;
    private static final long C_CHATTING_TIME_MILLISECONDS = 30000; //300000;
    private static final long C_CHOOSING_TIME_MILLISECONDS = 20000; //60000;

    protected GameSessionProcessorImplFake() {
        super();
    }

    public static GameSessionProcessorImplFake getInstance() {
        return new GameSessionProcessorImplFake();
    }

    @Override
    protected Error execSearchingState() {
        GameSessionImplFakeStateSearching gameSessionImplFakeSearching =
                (GameSessionImplFakeStateSearching) m_gameSessionState;

        if (gameSessionImplFakeSearching.getStartSearchingTime() + C_SEARCHING_TIME_MILLISECONDS >
                System.currentTimeMillis())
        {
            return null;
        }

        List<RemoteProfilePublic> remoteProfilePublicList =
                new ArrayList<RemoteProfilePublic>() {
                    {
                        add(RemoteProfilePublic.getInstance(1, "user1"));
                    }
                };

        RemoteFoundGameData remoteFoundGameData =
                RemoteFoundGameData.getInstance(
                        0,
                        System.currentTimeMillis(),
                        C_CHATTING_TIME_MILLISECONDS,
                        C_CHOOSING_TIME_MILLISECONDS,
                        remoteProfilePublicList);

        m_callback.gameFound(remoteFoundGameData);

        m_gameSessionState =
                GameSessionImplFakeStateChatting.getInstance();

        // checking for null state..

        m_foundGameData = remoteFoundGameData;

        return null;
    }

    @Override
    protected Error execChattingState() {
        GameSessionImplFakeStateChatting gameSessionImplFakeStateChatting =
                (GameSessionImplFakeStateChatting) m_gameSessionState;

        if (m_foundGameData.getStartSessionTime() +
            m_foundGameData.getChattingStageDuration() <=
            System.currentTimeMillis())
        {
            // todo: moving to CHOOSING state..

            return null;
        }

        RemoteMessage remoteMessage = gameSessionImplFakeStateChatting.takeFakePendingMessage();

        if (remoteMessage == null) return null;

        Log.d("TEST", "execChattingState(); Remote Message: " + remoteMessage.getText());

        m_callback.messageReceived(remoteMessage);

        return null;
    }

    @Override
    protected Error execChoosingState() {
        return null;
    }

    @Override
    protected Error execEndingState() {
        return null;
    }

    @Override
    public Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching)
    {
        GameSessionImplFakeStateSearching gameSessionStateSearching =
                GameSessionImplFakeStateSearching.getInstance(System.currentTimeMillis());

        if (gameSessionStateSearching == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.SEARCHING_STATE_CREATION_FAILED.getResourceCode(),
                    GameSessionProcessorErrorEnum.SEARCHING_STATE_CREATION_FAILED.isCritical());

            return error;
        }

        m_gameSessionState = gameSessionStateSearching;

        return null;
    }

    @Override
    public Error stopSearchingCommandProcessing(
            @NonNull final CommandStopSearching commandStopSearching)
    {


        Thread.currentThread().interrupt();

        return null;
    }

    @Override
    public Error sendMessageCommandProcessing(
            @NonNull final CommandSendMessage commandSendMessage)
    {


        return null;
    }

    @Override
    public Error chooseUsersCommandProcessing(
            @NonNull final CommandChooseUsers commandChooseUsers)
    {


        return null;
    }

    @Override
    public Error leaveCommandProcessing(
            @NonNull final CommandLeave commandLeave)
    {


        return null;
    }
}
