package com.qubacy.interlocutor.data.game.export.processor;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessorCallback;
import com.qubacy.interlocutor.data.game.internal.processor.command.Command;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
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

    public enum ErrorType {
        UNKNOWN_COMMAND_TYPE(R.string.error_game_session_processor_unknown_command),
        ;

        final private int m_resourceCode;

        private ErrorType(final int resourceCode) {
            m_resourceCode = resourceCode;
        }

        public int getResourceCode() {
            return m_resourceCode;
        }
    }

    final protected BlockingQueue<Command> m_commandQueue;

    protected Context m_context = null;
    protected GameSessionProcessorCallback m_callback = null;

    protected Thread m_thread = null;

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

            Command curCommand = m_commandQueue.poll();

            if (curCommand == null) continue;

            Error commandProcessingError = processCommand(curCommand);

            if (commandProcessingError != null) {
                m_callback.errorOccurred(commandProcessingError);

                continue;
            }
        }
    }

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

        return Error.getInstance(
                m_context.getString(ErrorType.UNKNOWN_COMMAND_TYPE.getResourceCode()),
                true);
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
    public boolean chooseUsers(@NonNull final List<RemoteProfilePublic> chosenUserList) {
        CommandChooseUsers commandChooseUsers =
                CommandChooseUsers.getInstance(chosenUserList);

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

    public abstract Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching);
    public abstract Error stopSearchingCommandProcessing(
            @NonNull final CommandStopSearching commandStopSearching);

    public abstract Error sendMessageCommandProcessing(
            @NonNull final CommandSendMessage commandSendMessage);
    public abstract Error chooseUsersCommandProcessing(
            @NonNull final CommandChooseUsers commandChooseUsers);

    public abstract Error leaveCommandProcessing(
            @NonNull final CommandLeave commandLeave);
}
