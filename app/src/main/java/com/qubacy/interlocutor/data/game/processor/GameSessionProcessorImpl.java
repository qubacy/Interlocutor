package com.qubacy.interlocutor.data.game.processor;

import android.content.Context;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.processor.command.CommandChooseUsers;
import com.qubacy.interlocutor.data.game.processor.command.CommandLeave;
import com.qubacy.interlocutor.data.game.processor.command.CommandSendMessage;
import com.qubacy.interlocutor.data.game.processor.command.CommandStartSearching;
import com.qubacy.interlocutor.data.game.processor.command.CommandStopSearching;
import com.qubacy.interlocutor.data.general.struct.error.Error;

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
