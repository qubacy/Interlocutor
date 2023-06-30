package com.qubacy.interlocutor.data.game.processor;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.struct.FoundGameData;
import com.qubacy.interlocutor.data.game.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.general.struct.error.Error;
import com.qubacy.interlocutor.data.general.struct.profile.other.OtherProfile;

import java.util.List;

public interface GameSessionProcessorCallback {
    public void gameFound(@NonNull final FoundGameData foundGameData);
    public void gameSearchingAborted();
    public void errorOccurred(@NonNull final Error error);
    public void messageSent();
    public void messageReceived(@NonNull final RemoteMessage message);
    public void usersMadeChoice(@NonNull final List<OtherProfile> matchedUserList);
}
