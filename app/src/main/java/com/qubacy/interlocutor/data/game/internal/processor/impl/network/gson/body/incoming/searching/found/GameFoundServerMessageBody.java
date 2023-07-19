package com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.searching.found;

import com.qubacy.interlocutor.data.game.internal.processor.impl.network.gson.body.incoming.ServerMessageBody;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;

public class GameFoundServerMessageBody extends ServerMessageBody {
    public static final String C_FOUND_GAME_DATA_PROP_NAME = "foundGameData";

    private final RemoteFoundGameData m_foundGameData;

    protected GameFoundServerMessageBody(final RemoteFoundGameData foundGameData) {
        m_foundGameData = foundGameData;
    }

    public static GameFoundServerMessageBody getInstance(
            final RemoteFoundGameData foundGameData)
    {
        if (foundGameData == null) return null;

        return new GameFoundServerMessageBody(foundGameData);
    }

    public RemoteFoundGameData getFoundGameData() {
        return m_foundGameData;
    }
}
