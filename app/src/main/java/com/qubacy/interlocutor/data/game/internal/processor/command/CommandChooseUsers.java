package com.qubacy.interlocutor.data.game.internal.processor.command;

import java.util.List;

public class CommandChooseUsers extends Command {
    private final List<Integer> m_chosenUserIdList;

    protected CommandChooseUsers(
            final List<Integer> chosenUserIdList)
    {
        super();

        m_chosenUserIdList = chosenUserIdList;
    }

    public static CommandChooseUsers getInstance(
            final List<Integer> chosenUserIdList)
    {
        if (chosenUserIdList == null) return null;
        if (chosenUserIdList.isEmpty()) return null;

        return new CommandChooseUsers(chosenUserIdList);
    }

    @Override
    public CommandType getType() {
        return CommandType.CHOOSE_USERS;
    }

    public List<Integer> getChosenUserIdList() {
        return m_chosenUserIdList;
    }
}
