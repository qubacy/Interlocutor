package com.qubacy.interlocutor.data.game.internal.processor.command;

import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfilePublic;

import java.util.List;

public class CommandChooseUsers extends Command {
    private final List<RemoteProfilePublic> m_chosenUserList;

    protected CommandChooseUsers(
            final List<RemoteProfilePublic> chosenUserList)
    {
        super();

        m_chosenUserList = chosenUserList;
    }

    public static CommandChooseUsers getInstance(
            final List<RemoteProfilePublic> chosenUserList)
    {
        if (chosenUserList == null) return null;
        if (chosenUserList.isEmpty()) return null;

        return new CommandChooseUsers(chosenUserList);
    }

    @Override
    public CommandType getType() {
        return CommandType.CHOOSE_USERS;
    }

    public List<RemoteProfilePublic> getChosenUserList() {
        return m_chosenUserList;
    }
}
