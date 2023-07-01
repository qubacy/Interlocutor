package com.qubacy.interlocutor.data.game.internal.processor;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.internal.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;

import java.io.Serializable;

/*
*
* This class is in charge to IMPLEMENT the CLIENT-SERVER RELATION LOGIC;
*
*/
public class GameSessionProcessorImpl extends GameSessionProcessor
        implements Serializable
{
    protected GameSessionProcessorImpl() {
        super();
    }

    public static GameSessionProcessorImpl getInstance() {
        return new GameSessionProcessorImpl();
    }

    @Override
    public Error startSearchingCommandProcessing(
            @NonNull final CommandStartSearching commandStartSearching)
    {


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
