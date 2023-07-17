package com.qubacy.interlocutor.data.game.internal.processor.impl;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.internal.processor.error.GameSessionProcessorErrorEnum;
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

    protected GameSessionProcessorImpl() {
        super();
    }

    public static GameSessionProcessorImpl getInstance() {
        GameSessionProcessorImpl gameSessionProcessor =
                new GameSessionProcessorImpl();
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
        // todo: reading and processing a network source..

        return null;
    }

    @Override
    protected Error execChattingState() {
        // todo: reading and processing a network source..

        return null;
    }

    @Override
    protected Error execChoosingState() {
        // todo: reading and processing a network source..

        return null;
    }

    @Override
    protected Error execEndingState() {
        // todo: reading and processing a network source..

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

    @Override
    public void onServerMessageReceived(
            @NonNull final String serverMessage)
    {
        // todo: processing a message..
    }
}
