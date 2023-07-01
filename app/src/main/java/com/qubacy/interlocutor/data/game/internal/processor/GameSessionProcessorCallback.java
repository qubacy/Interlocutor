package com.qubacy.interlocutor.data.game.internal.processor;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;

import java.util.List;

public interface GameSessionProcessorCallback {
    public void gameFound(@NonNull final FoundGameData foundGameData);
    public void gameSearchingAborted();
    public void errorOccurred(@NonNull final Error error);
    public void messageSent();
    public void messageReceived(@NonNull final RemoteMessage message);
    public void usersMadeChoice(@NonNull final List<RemoteProfile> matchedUserList);
}
