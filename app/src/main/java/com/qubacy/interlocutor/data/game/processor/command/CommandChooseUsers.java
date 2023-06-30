package com.qubacy.interlocutor.data.game.processor.command;

import com.qubacy.interlocutor.data.general.struct.profile.other.OtherProfilePublic;

import java.util.List;

public class CommandChooseUsers extends Command {
    private final List<OtherProfilePublic> m_chosenUserList;

    protected CommandChooseUsers(
            final List<OtherProfilePublic> chosenUserList)
    {
        super();

        m_chosenUserList = chosenUserList;
    }

    public static CommandChooseUsers getInstance(
            final List<OtherProfilePublic> chosenUserList)
    {
        if (chosenUserList == null) return null;
        if (chosenUserList.isEmpty()) return null;

        return new CommandChooseUsers(chosenUserList);
    }

    @Override
    public CommandType getType() {
        return CommandType.CHOOSE_USERS;
    }

    public List<OtherProfilePublic> getChosenUserList() {
        return m_chosenUserList;
    }
}
