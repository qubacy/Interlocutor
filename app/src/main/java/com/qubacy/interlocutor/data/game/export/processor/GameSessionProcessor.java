package com.qubacy.interlocutor.data.game.export.processor;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessorCallback;
import com.qubacy.interlocutor.data.game.internal.processor.command.Command;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.game.internal.processor.error.GameSessionProcessorErrorEnum;
import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionState;
import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChatting;
import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateChoosing;
import com.qubacy.interlocutor.data.game.internal.processor.state.GameSessionStateSearching;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/*
*
* This Processor should be executed in BACKGROUND by it's definition;
*
*/
public abstract class GameSessionProcessor implements Serializable {
    public static final int C_EXEC_TIMEOUT = 400;

    final protected BlockingQueue<Command> m_commandQueue;

    protected Context m_context = null;
    protected GameSessionProcessorCallback m_callback = null;

    protected Thread m_thread = null;

    protected GameSessionState m_gameSessionState = null;
    protected RemoteFoundGameData m_foundGameData = null;

    protected GameSessionProcessor() {
        m_commandQueue = new LinkedBlockingQueue<Command>();
    }

    public boolean launch(
            final GameSessionProcessorCallback callback,
            final Context context)
    {
        if (callback == null || context == null)
            return false;

        m_callback = callback;
        m_context = context;

        m_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                exec();
            }
        });

        m_thread.start();

        return true;
    }
    protected void exec() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (!Thread.currentThread().isInterrupted()) {
            SystemClock.sleep(C_EXEC_TIMEOUT);

            Error executionError = execIteration();

            if (executionError != null) {
                m_callback.errorOccurred(executionError);

                continue;
            }
        }
    }
    protected Error execIteration() {
        Error commandError = execCommand();

        if (commandError != null)
            return commandError;

        Error stateError = execState();

        if (stateError != null)
            return stateError;

        return null;
    }
    protected Error execCommand() {
        Command curCommand = m_commandQueue.poll();

        if (curCommand == null) return null;

        Error commandProcessingError = processCommand(curCommand);

        if (commandProcessingError != null)
            return commandProcessingError;

        return null;
    }
    protected Error execState() {
        if (m_gameSessionState == null) return null;

        switch (m_gameSessionState.getType()) {
            case SEARCHING: return execSearchingState();
            case CHATTING: return execChattingState();
            case CHOOSING: return execChoosingState();
            case ENDING: return execEndingState();
        }

        Error error =
            ErrorUtility.getErrorByStringResourceCodeAndFlag(
                m_context,
                GameSessionProcessorErrorEnum.UNKNOWN_STATE_TYPE.getResourceCode(),
                GameSessionProcessorErrorEnum.UNKNOWN_STATE_TYPE.isCritical());

        return error;
    }

    protected abstract Error execSearchingState();
    protected abstract Error execChattingState();
    protected abstract Error execChoosingState();
    protected abstract Error execEndingState();

    public boolean isRunning() {
        return !m_thread.isInterrupted();
    }
    public void stop() {
        m_thread.interrupt();
    }

    protected Error processCommand(final Command command) {
        switch (command.getType()) {
            case START_SEARCHING:
                return startSearchingCommandProcessing((CommandStartSearching) command);
            case STOP_SEARCHING:
                return stopSearchingCommandProcessing((CommandStopSearching) command);
            case SEND_MESSAGE:
                return sendMessageCommandProcessing((CommandSendMessage) command);
            case CHOOSE_USERS:
                return chooseUsersCommandProcessing((CommandChooseUsers) command);
            case LEAVE:
                return leaveCommandProcessing((CommandLeave) command);
        }

        Error error =
            ErrorUtility.getErrorByStringResourceCodeAndFlag(
                m_context,
                GameSessionProcessorErrorEnum.UNKNOWN_COMMAND_TYPE.getResourceCode(),
                GameSessionProcessorErrorEnum.UNKNOWN_COMMAND_TYPE.isCritical());

        return error;
    }

    public boolean startSearching(@NonNull final Profile localProfile) {
        CommandStartSearching commandStartSearching =
                CommandStartSearching.getInstance(localProfile);

        if (commandStartSearching == null) return false;

        try {
            m_commandQueue.put(commandStartSearching);

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }
    public boolean stopSearching() {
        CommandStopSearching commandStopSearching =
                CommandStopSearching.getInstance();

        if (commandStopSearching == null) return false;

        try {
            m_commandQueue.put(commandStopSearching);

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public boolean sendMessage(@NonNull final Message message) {
        CommandSendMessage commandSendMessage =
                CommandSendMessage.getInstance(message);

        if (commandSendMessage == null) return false;

        try {
            m_commandQueue.put(commandSendMessage);

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }
    public boolean chooseUsers(@NonNull final List<Integer> chosenUserIdList) {
        CommandChooseUsers commandChooseUsers =
                CommandChooseUsers.getInstance(chosenUserIdList);

        if (commandChooseUsers == null) return false;

        try {
            m_commandQueue.put(commandChooseUsers);

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public boolean leave() {
        CommandLeave commandLeave = CommandLeave.getInstance();

        if (commandLeave == null) return false;

        try {
            m_commandQueue.put(commandLeave);

        } catch (InterruptedException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching)
    {
        if (m_gameSessionState != null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.getResourceCode(),
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.isCritical());

            return error;
        }

        return null;
    }
    public Error stopSearchingCommandProcessing(
            @NonNull final CommandStopSearching commandStopSearching)
    {
        if (!(m_gameSessionState instanceof GameSessionStateSearching)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.getResourceCode(),
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.isCritical());

            return error;
        }

        return null;
    }

    public Error sendMessageCommandProcessing(
            @NonNull final CommandSendMessage commandSendMessage)
    {
        if (!(m_gameSessionState instanceof GameSessionStateChatting)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.getResourceCode(),
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.isCritical());

            return error;
        }

        return null;
    }
    public Error chooseUsersCommandProcessing(
            @NonNull final CommandChooseUsers commandChooseUsers)
    {
        if (!(m_gameSessionState instanceof GameSessionStateChoosing)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.getResourceCode(),
                    GameSessionProcessorErrorEnum.ILLEGAL_STATE.isCritical());

            return error;
        }

        return null;
    }

    public abstract Error leaveCommandProcessing(
            @NonNull final CommandLeave commandLeave);
}
