package com.qubacy.interlocutor.data.game.internal.processor.impl;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.internal.processor.error.GameSessionProcessorErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.error.GameSessionProcessorImplErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.MessageDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.MessageSerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover.ChattingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen.UsersChosenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.results.ResultsGottenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.stop.StopSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.WebSocketClient;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.listener.WebSocketListenerCallback;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.listener.WebSocketListenerImpl;
import com.qubacy.interlocutor.data.game.internal.processor.impl.state.GameSessionImplStateSearching;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;

/*
*
* This class is in charge to IMPLEMENT the CLIENT-SERVER RELATION LOGIC;
*
*/
public class GameSessionProcessorImpl extends GameSessionProcessor
        implements
            WebSocketListenerCallback
{
    public static final String C_URL = "ws://";

    private WebSocketClient m_webSocketClient = null;

    private final Gson m_gson;

    protected GameSessionProcessorImpl(
            final Gson gson)
    {
        super();

        m_gson = gson;
    }

    public static GameSessionProcessorImpl getInstance() {
        MessageSerializer messageSerializer = new MessageSerializer();
        MessageDeserializer messageDeserializer = new MessageDeserializer();

        Gson gson =
                new GsonBuilder().
                        registerTypeAdapter(Message.class, messageSerializer).
                        registerTypeAdapter(Message.class, messageDeserializer).
                        create();

        if (gson == null) return null;

        GameSessionProcessorImpl gameSessionProcessor =
                new GameSessionProcessorImpl(gson);
        WebSocketListenerImpl webSocketListener =
                WebSocketListenerImpl.getInstance(gameSessionProcessor);
        WebSocketClient webSocketClient =
                WebSocketClient.getInstance(C_URL, webSocketListener);

        if (!gameSessionProcessor.setWebSocketClient(webSocketClient))
            return null;

        return gameSessionProcessor;
    }

    protected boolean setWebSocketClient(final WebSocketClient webSocketClient) {
        if (webSocketClient == null) return false;

        m_webSocketClient = webSocketClient;

        return true;
    }

    @Override
    protected Error execSearchingState() {
        // todo: processing the state..

        return null;
    }

    @Override
    protected Error execChattingState() {
        // todo: processing the state..

        return null;
    }

    @Override
    protected Error execChoosingState() {
        // todo: processing the state..

        return null;
    }

    @Override
    protected Error execEndingState() {
        // todo: processing the state..

        return null;
    }

    @Override
    public Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching)
    {
        GameSessionImplStateSearching gameSessionStateSearching =
                GameSessionImplStateSearching.getInstance();

        if (gameSessionStateSearching == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.SEARCHING_STATE_CREATION_FAILED.getResourceCode(),
                    GameSessionProcessorErrorEnum.SEARCHING_STATE_CREATION_FAILED.isCritical());

            return error;
        }

        // todo: processing a start searching command..

        return null;
    }

    @Override
    public Error stopSearchingCommandProcessing(
            @NonNull final CommandStopSearching commandStopSearching)
    {
        // todo: processing a searching stop command..

        Thread.currentThread().interrupt();

        return null;
    }

    @Override
    public Error sendMessageCommandProcessing(
            @NonNull final CommandSendMessage commandSendMessage)
    {
        // todo: processing a sending message command..

        return null;
    }

    @Override
    public Error chooseUsersCommandProcessing(
            @NonNull final CommandChooseUsers commandChooseUsers)
    {
        // todo: processing a choosing users command..

        return null;
    }

    @Override
    public Error leaveCommandProcessing(
            @NonNull final CommandLeave commandLeave)
    {
        // todo: processing a leave command..

        return null;
    }

    @Override
    public void onServerMessageReceived(
            @NonNull final String serverMessage)
    {
        Message message = m_gson.fromJson(serverMessage, Message.class);

        if (message == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE.isCritical());

            m_callback.errorOccurred(error);

            return;
        }

        Error processingError =
                processServerMessage(
                        message.getOperation(), (ServerMessageBody) message.getMessageBody());

        if (processingError != null) {
            m_callback.errorOccurred(processingError);

            return;
        }
    }

    private Error processServerMessage(
            final OperationEnum operationEnum,
            final ServerMessageBody serverMessageBody)
    {
        if (serverMessageBody.getError() != null) {
            return Error.getInstance(serverMessageBody.getError().getMessage(), true);
        }

        switch (operationEnum) {
            case SEARCHING_START: return
                    processSearchingStartServerMessage(
                            (StartSearchingServerMessageBody) serverMessageBody);
            case SEARCHING_STOP: return
                    processSearchingStopServerMessage(
                            (StopSearchingServerMessageBody) serverMessageBody);
            case SEARCHING_GAME_FOUND: return
                    processGameFoundServerMessage(
                            (GameFoundServerMessageBody) serverMessageBody);
            case CHATTING_NEW_MESSAGE: return
                    processNewMessageServerMessage(
                            (NewChatMessageServerMessageBody) serverMessageBody);
            case CHATTING_STAGE_IS_OVER: return
                    processChattingStageIsOverServerMessage(
                            (ChattingStageIsOverServerMessageBody) serverMessageBody);
            case CHOOSING_USERS_CHOSEN: return
                    processUsersChosenServerMessage(
                            (UsersChosenServerMessageBody) serverMessageBody);
            case CHOOSING_STAGE_IS_OVER: return
                    processChoosingStageIsOverServerMessage(
                            (ChoosingStageIsOverServerMessageBody) serverMessageBody);
            case RESULTS_MATCHED_USERS_GOTTEN: return
                    processResultsGottenServerMessage(
                            (ResultsGottenServerMessageBody) serverMessageBody);
        }

        Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameSessionProcessorImplErrorEnum.UNKNOWN_OPERATION_TYPE.getResourceCode(),
                        GameSessionProcessorImplErrorEnum.UNKNOWN_OPERATION_TYPE.isCritical());

        return error;
    }

    private Error processSearchingStartServerMessage(
            final StartSearchingServerMessageBody startSearchingServerMessageBody)
    {
        if (startSearchingServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        return null;
    }

    private Error processSearchingStopServerMessage(
            final StopSearchingServerMessageBody stopSearchingServerMessageBody)
    {
        if (stopSearchingServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        return null;
    }

    private Error processGameFoundServerMessage(
            final GameFoundServerMessageBody gameFoundServerMessageBody)
    {
        if (gameFoundServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        // todo: processing game found data..



        return null;
    }

    private Error processNewMessageServerMessage(
            final NewChatMessageServerMessageBody newChatMessageServerMessageBody)
    {
        if (newChatMessageServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        // todo: processing a new chat message..

        return null;
    }

    private Error processChattingStageIsOverServerMessage(
            final ChattingStageIsOverServerMessageBody chattingStageIsOverServerMessageBody)
    {
        if (chattingStageIsOverServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        // todo: processing a signal of the end of a chatting stage;



        return null;
    }

    private Error processUsersChosenServerMessage(
            final UsersChosenServerMessageBody usersChosenServerMessageBody)
    {
        if (usersChosenServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        return null;
    }

    private Error processChoosingStageIsOverServerMessage(
            final ChoosingStageIsOverServerMessageBody choosingStageIsOverServerMessageBody)
    {
        if (choosingStageIsOverServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        // todo: processing a signal of the end of a choosing stage..

        return null;
    }

    private Error processResultsGottenServerMessage(
            final ResultsGottenServerMessageBody resultsGottenServerMessageBody)
    {
        if (resultsGottenServerMessageBody == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE_BODY.isCritical());

            return error;
        }

        // todo: processing game results..


        return null;
    }
}
