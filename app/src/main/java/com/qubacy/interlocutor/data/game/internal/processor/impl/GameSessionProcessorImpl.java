package com.qubacy.interlocutor.data.game.internal.processor.impl;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.internal.processor.error.GameSessionProcessorErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.error.GameSessionProcessorImplErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommand;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandConnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandDisconnected;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandFailureOccurred;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.callback.NetworkCallbackCommandMessageReceived;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.error.NetworkServerErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.Message;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.MessageDeserializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.MessageSerializer;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.OperationEnum;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageError;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.newmessage.NewChatMessageServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.chatting.stageover.ChattingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.userschosen.UsersChosenServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.choosing.stageover.ChoosingStageIsOverServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found.GameFoundServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.start.StartSearchingServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.ClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.chatting.newmessage.NewMessageClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.choosing.makechoice.UsersChosenClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.start.StartSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.outgoing.searching.stop.StopSearchingClientMessageBody;
import com.qubacy.interlocutor.data.game.internal.processor.impl.network.websocket.WebSocketClient;
import com.qubacy.interlocutor.data.game.internal.processor.impl.state.GameSessionImplStateChatting;
import com.qubacy.interlocutor.data.game.internal.processor.impl.state.GameSessionImplStateChoosing;
import com.qubacy.interlocutor.data.game.internal.processor.impl.state.GameSessionImplStateResults;
import com.qubacy.interlocutor.data.game.internal.processor.impl.state.GameSessionImplStateSearching;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/*
*
* This class is in charge to IMPLEMENT the CLIENT-SERVER RELATION LOGIC;
*
*/
public class GameSessionProcessorImpl extends GameSessionProcessor
{
    public static final String C_URL = "http://10.0.2.2:47777/";

    private final WebSocketClient m_webSocketClient;

    private final Gson m_gson;

    private final BlockingQueue<NetworkCallbackCommand> m_networkCallbackCommandQueue;

    protected GameSessionProcessorImpl(
            final Gson gson,
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final WebSocketClient webSocketClient)
    {
        super();

        m_gson = gson;
        m_networkCallbackCommandQueue = networkCallbackCommandQueue;
        m_webSocketClient = webSocketClient;
    }

    public static GameSessionProcessorImpl getInstance(
            final BlockingQueue<NetworkCallbackCommand> networkCallbackCommandQueue,
            final WebSocketClient webSocketClient)
    {
        if (networkCallbackCommandQueue == null ||
            webSocketClient == null)
        {
            return null;
        }

        MessageSerializer messageSerializer = new MessageSerializer();
        MessageDeserializer messageDeserializer = new MessageDeserializer();

        Gson gson =
                new GsonBuilder().
                        registerTypeAdapter(Message.class, messageSerializer).
                        registerTypeAdapter(Message.class, messageDeserializer).
                        create();

        if (gson == null) return null;

        return new GameSessionProcessorImpl(
                gson, networkCallbackCommandQueue, webSocketClient);
    }

    @Override
    protected Error execIteration() {
        Error execError = super.execIteration();

        if (execError != null) return execError;

        NetworkCallbackCommand callbackCommand =
                m_networkCallbackCommandQueue.poll();

        if (callbackCommand == null) return null;

        return processCallbackCommand(callbackCommand);
    }

    private Error processCallbackCommand(
            final NetworkCallbackCommand callbackCommand)
    {
        switch (callbackCommand.getType()) {
            case CONNECTED: return
                    processConnectedCallbackCommand(
                            (NetworkCallbackCommandConnected) callbackCommand);
            case FAILURE_OCCURRED: return
                    processFailureOccurredCallback(
                            (NetworkCallbackCommandFailureOccurred) callbackCommand);
            case MESSAGE_RECEIVED: return
                    processMessageReceivedCallbackCommand(
                            (NetworkCallbackCommandMessageReceived) callbackCommand);
            case DISCONNECTED: return
                    processDisconnectedCallbackCommand(
                            (NetworkCallbackCommandDisconnected) callbackCommand);
        }

        Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameSessionProcessorImplErrorEnum.
                                UNKNOWN_NETWORK_CALLBACK_COMMAND_TYPE.getResourceCode(),
                        GameSessionProcessorImplErrorEnum.
                                UNKNOWN_NETWORK_CALLBACK_COMMAND_TYPE.isCritical());

        return error;
    }

    private Error processConnectedCallbackCommand(
            final NetworkCallbackCommandConnected commandConnected)
    {
        // is there anything to do here?..

        return null;
    }

    private Error processFailureOccurredCallback(
            final NetworkCallbackCommandFailureOccurred commandFailureOccurred)
    {
        m_callback.onDisconnection(true);

        return null;
    }

    private Error processMessageReceivedCallbackCommand(
            final NetworkCallbackCommandMessageReceived commandMessageReceived)
    {
        Message message = m_gson.fromJson(commandMessageReceived.getMessage(), Message.class);

        if (message == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.NULL_SERVER_MESSAGE.isCritical());

            return error;
        }

        Error processingError =
                processServerMessage(
                        message.getOperation(),
                        (ServerMessageBody) message.getMessageBody());

        if (processingError != null) return processingError;

        return null;
    }

    private Error processDisconnectedCallbackCommand(
            final NetworkCallbackCommandDisconnected commandDisconnected)
    {
        m_callback.onDisconnection(false);

        return null;
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

    private Error generateJsonClientMessageAndSend(
            final OperationEnum operation,
            final ClientMessageBody messageBody)
    {
        Message message = Message.getInstance(operation, messageBody);
        String serializedMessage = m_gson.toJson(message);

        if (!m_webSocketClient.sendMessage(serializedMessage)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorImplErrorEnum.SENDING_CLIENT_MESSAGE_FAILED.getResourceCode(),
                    GameSessionProcessorImplErrorEnum.SENDING_CLIENT_MESSAGE_FAILED.isCritical());

            return error;
        }

        return null;
    }

    @Override
    public Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching)
    {
        StartSearchingClientMessageBody messageBody =
                StartSearchingClientMessageBody.getInstance(
                        commandStartSearching.getLocalProfile());

        Error sendingError =
                generateJsonClientMessageAndSend(OperationEnum.SEARCHING_START, messageBody);

        if (sendingError != null) return sendingError;

        return null;
    }

    @Override
    public Error stopSearchingCommandProcessing(
            @NonNull final CommandStopSearching commandStopSearching)
    {
        StopSearchingClientMessageBody messageBody = new StopSearchingClientMessageBody();

        Error sendingError =
                generateJsonClientMessageAndSend(OperationEnum.SEARCHING_STOP, messageBody);

        if (sendingError != null) return sendingError;

        m_webSocketClient.close();
        Thread.currentThread().interrupt();

        return null;
    }

    @Override
    public Error sendMessageCommandProcessing(
            @NonNull final CommandSendMessage commandSendMessage)
    {
        NewMessageClientMessageBody messageBody =
                NewMessageClientMessageBody.getInstance(commandSendMessage.getMessage());

        Error sendingError =
                generateJsonClientMessageAndSend(OperationEnum.CHATTING_NEW_MESSAGE, messageBody);

        if (sendingError != null) return sendingError;

        return null;
    }

    @Override
    public Error chooseUsersCommandProcessing(
            @NonNull final CommandChooseUsers commandChooseUsers)
    {
        UsersChosenClientMessageBody messageBody =
                UsersChosenClientMessageBody.getInstance(commandChooseUsers.getChosenUserIdList());

        Error sendingError =
                generateJsonClientMessageAndSend(OperationEnum.CHOOSING_USERS_CHOSEN, messageBody);

        if (sendingError != null) return sendingError;

        return null;
    }

    @Override
    public Error leaveCommandProcessing(
            @NonNull final CommandLeave commandLeave)
    {
        // todo: processing a leave command.. ?

        return null;
    }

    private Error processServerMessage(
            final OperationEnum operationEnum,
            final ServerMessageBody serverMessageBody)
    {
        if (serverMessageBody.getError() != null) {
            Error error = generateErrorFromServerError(serverMessageBody.getError());

            return error;
        }

        switch (operationEnum) {
            case SEARCHING_START: return
                    processSearchingStartServerMessage(
                            (StartSearchingServerMessageBody) serverMessageBody);
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
        }

        Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                        m_context,
                        GameSessionProcessorImplErrorEnum.UNKNOWN_OPERATION_TYPE.getResourceCode(),
                        GameSessionProcessorImplErrorEnum.UNKNOWN_OPERATION_TYPE.isCritical());

        return error;
    }

    private Error generateErrorFromServerError(
            final ServerMessageError serverMessageError)
    {
        NetworkServerErrorEnum serverError =
                NetworkServerErrorEnum.getNetworkServerErrorById(serverMessageError.getId());

        if (serverError == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorImplErrorEnum.UNKNOWN_SERVER_ERROR.getResourceCode(),
                            GameSessionProcessorImplErrorEnum.UNKNOWN_SERVER_ERROR.isCritical());

            return error;
        }

        Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                        m_context,
                        serverError.getStringResId(),
                        serverError.isCritical());

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

        ServerMessageError serverError = startSearchingServerMessageBody.getError();

        if (serverError != null)
            return generateErrorFromServerError(serverError);

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

        m_gameSessionState = gameSessionStateSearching;

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

        ServerMessageError serverError = gameFoundServerMessageBody.getError();

        if (serverError != null)
            return generateErrorFromServerError(serverError);

        m_foundGameData = gameFoundServerMessageBody.getFoundGameData();

        GameSessionImplStateChatting gameSessionStateChatting =
                GameSessionImplStateChatting.getInstance();

        if (gameSessionStateChatting == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorErrorEnum.CHATTING_STATE_CREATION_FAILED.getResourceCode(),
                            GameSessionProcessorErrorEnum.CHATTING_STATE_CREATION_FAILED.isCritical());

            return error;
        }

        m_gameSessionState = gameSessionStateChatting;

        m_callback.gameFound(m_foundGameData);

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

        ServerMessageError serverError = newChatMessageServerMessageBody.getError();

        if (serverError != null)
            return generateErrorFromServerError(serverError);

        m_callback.messageReceived(newChatMessageServerMessageBody.getMessage());

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

        ServerMessageError serverError = chattingStageIsOverServerMessageBody.getError();

        if (serverError != null)
            return generateErrorFromServerError(serverError);

        GameSessionImplStateChoosing gameSessionStateChoosing =
                GameSessionImplStateChoosing.getInstance();

        if (gameSessionStateChoosing == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorErrorEnum.CHOOSING_STATE_CREATION_FAILED.getResourceCode(),
                            GameSessionProcessorErrorEnum.CHOOSING_STATE_CREATION_FAILED.isCritical());

            return error;
        }

        m_gameSessionState = gameSessionStateChoosing;

        m_callback.onChattingPhaseIsOver();

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

        ServerMessageError serverError = usersChosenServerMessageBody.getError();

        if (serverError != null)
            return generateErrorFromServerError(serverError);

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

        ServerMessageError serverError = choosingStageIsOverServerMessageBody.getError();

        if (serverError != null)
            return generateErrorFromServerError(serverError);

        GameSessionImplStateResults gameSessionStateResults =
                GameSessionImplStateResults.getInstance();

        if (gameSessionStateResults == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            GameSessionProcessorErrorEnum.RESULTS_STATE_CREATION_FAILED.getResourceCode(),
                            GameSessionProcessorErrorEnum.RESULTS_STATE_CREATION_FAILED.isCritical());

            return error;
        }

        m_gameSessionState = gameSessionStateResults;

        m_callback.onChoosingPhaseIsOver(
                (ArrayList<MatchedUserProfileData>) choosingStageIsOverServerMessageBody.
                        getMatchedUsers());

        return null;
    }
}
