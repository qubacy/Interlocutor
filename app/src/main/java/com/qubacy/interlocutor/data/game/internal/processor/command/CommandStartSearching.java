package com.qubacy.interlocutor.data.game.internal.processor.command;

import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

public class CommandStartSearching extends Command {
    final private Profile m_localProfile;

    protected CommandStartSearching(
            final Profile localProfilePublic)
    {
        super();

        m_localProfile = localProfilePublic;
    }

    public static CommandStartSearching getInstance(
            final Profile localProfilePublic)
    {
        if (localProfilePublic == null) return null;

        return new CommandStartSearching(localProfilePublic);
    }

    public Profile getLocalProfile() {
        return m_localProfile;
    }

    @Override
    public CommandType getType() {
        return CommandType.START_SEARCHING;
    }
}
