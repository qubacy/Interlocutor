package com.qubacy.interlocutor.data.game.internal.processor.implfake;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.internal.processor.error.GameSessionProcessorErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.state.GameSessionImplFakeStateChatting;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.state.GameSessionImplFakeStateChoosing;
import com.qubacy.interlocutor.data.game.internal.processor.implfake.state.GameSessionImplFakeStateResults;
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
    private static final long C_SEARCHING_TIME_MILLISECONDS = 1000;
    private static final long C_CHATTING_TIME_MILLISECONDS = 2000; //300000;
    private static final long C_CHOOSING_TIME_MILLISECONDS = 2000; //60000;

    private static final int C_LOCAL_ID = 0;
    private static final int C_INTERLOCUTOR_ID = 1;

    private static final String C_TOPIC = "Board games";
    private static final String C_INTERLOCUTOR_USERNAME = "User1";

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
                        add(RemoteProfilePublic.
                                getInstance(C_INTERLOCUTOR_ID, C_INTERLOCUTOR_USERNAME));
                        add(RemoteProfilePublic.
                                getInstance(C_INTERLOCUTOR_ID + 1, "User2"));
                        add(RemoteProfilePublic.
                                getInstance(C_INTERLOCUTOR_ID + 2, "User3"));
                    }
                };

        RemoteFoundGameData remoteFoundGameData =
                RemoteFoundGameData.getInstance(
                        C_LOCAL_ID,
                        System.currentTimeMillis(),
                        C_CHATTING_TIME_MILLISECONDS,
                        C_CHOOSING_TIME_MILLISECONDS,
                        C_TOPIC,
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
            m_callback.onChattingPhaseIsOver();

            m_gameSessionState = GameSessionImplFakeStateChoosing.getInstance();

            return null;
        }

        RemoteMessage remoteMessage = gameSessionImplFakeStateChatting.takeFakePendingMessage();

        if (remoteMessage == null) return null;

        m_callback.messageReceived(remoteMessage);

        return null;
    }

    @Override
    protected Error execChoosingState() {
        GameSessionImplFakeStateChoosing gameSessionImplFakeStateChoosing =
                (GameSessionImplFakeStateChoosing) m_gameSessionState;

        if (m_foundGameData.getStartSessionTime() +
            m_foundGameData.getChattingStageDuration() +
            m_foundGameData.getChoosingStageDuration() <=
            System.currentTimeMillis())
        {
            ArrayList<MatchedUserProfileData> userProfileContactDataList =
                    new ArrayList<MatchedUserProfileData>()
                    {
                        {
                            List<Integer> chosenUserIdList =
                                    gameSessionImplFakeStateChoosing.getChosenUserIdList();

                            if (chosenUserIdList != null) {
                                for (final Integer userId : chosenUserIdList) {
                                    add(MatchedUserProfileData.getInstance(
                                            userId, "Contact of " + userId));
                                }
                            }
                        }
                    };

            m_callback.onChoosingPhaseIsOver(userProfileContactDataList);

            m_gameSessionState = GameSessionImplFakeStateResults.getInstance();

            return null;
        }

        return null;
    }

    @Override
    protected Error execEndingState() {
        // nothing to execute?..

        return null;
    }

    @Override
    public Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching)
    {
        Error error = super.startSearchingCommandProcessing(commandStartSearching);

        if (error != null) return error;

        GameSessionImplFakeStateSearching gameSessionStateSearching =
                GameSessionImplFakeStateSearching.getInstance(System.currentTimeMillis());

        if (gameSessionStateSearching == null) {
            Error stateSearchingError =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.SEARCHING_STATE_CREATION_FAILED.getResourceCode(),
                    GameSessionProcessorErrorEnum.SEARCHING_STATE_CREATION_FAILED.isCritical());

            return stateSearchingError;
        }

        m_gameSessionState = gameSessionStateSearching;

        return null;
    }

    @Override
    public Error stopSearchingCommandProcessing(
            @NonNull final CommandStopSearching commandStopSearching)
    {
        Error error = super.stopSearchingCommandProcessing(commandStopSearching);

        if (error != null) return error;

        Thread.currentThread().interrupt();

        return null;
    }

    @Override
    public Error sendMessageCommandProcessing(
            @NonNull final CommandSendMessage commandSendMessage)
    {
        Error error = super.sendMessageCommandProcessing(commandSendMessage);

        if (error != null) return error;

        GameSessionImplFakeStateChatting gameSessionImplFakeStateChatting =
                (GameSessionImplFakeStateChatting) m_gameSessionState;

        gameSessionImplFakeStateChatting.addFakePendingMessage(
                RemoteMessage.getInstance(C_LOCAL_ID, commandSendMessage.getMessage().getText()));

        return null;
    }

    @Override
    public Error chooseUsersCommandProcessing(
            @NonNull final CommandChooseUsers commandChooseUsers)
    {
        Error error = super.chooseUsersCommandProcessing(commandChooseUsers);

        if (error != null) return error;

        GameSessionImplFakeStateChoosing gameSessionImplFakeStateChoosing =
                (GameSessionImplFakeStateChoosing) m_gameSessionState;

        if (!gameSessionImplFakeStateChoosing.setChosenUserIdList(
                commandChooseUsers.getChosenUserIdList()))
        {
            Error settingError =
                ErrorUtility.
                    getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameSessionProcessorErrorEnum.CHOSEN_USERS_SETTING_FAILED.getResourceCode(),
                        GameSessionProcessorErrorEnum.CHOSEN_USERS_SETTING_FAILED.isCritical());

            return settingError;
        }

        return null;
    }

    @Override
    public Error leaveCommandProcessing(
            @NonNull final CommandLeave commandLeave)
    {


        return null;
    }
}
