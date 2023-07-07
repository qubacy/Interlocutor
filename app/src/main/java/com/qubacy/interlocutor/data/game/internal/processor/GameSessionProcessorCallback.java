package com.qubacy.interlocutor.data.game.internal.processor;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;

import java.util.List;

public interface GameSessionProcessorCallback {
    public void gameFound(@NonNull final RemoteFoundGameData foundGameData);
    public void gameSearchingAborted();
    public void errorOccurred(@NonNull final Error error);
    public void messageSent();
    public void messageReceived(@NonNull final RemoteMessage message);
    public void onChattingPhaseIsOver();
    public void usersMadeChoice(@NonNull final List<RemoteProfile> matchedUserList);
}
