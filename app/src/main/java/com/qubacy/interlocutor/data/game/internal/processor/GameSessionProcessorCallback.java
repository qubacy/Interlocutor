package com.qubacy.interlocutor.data.game.internal.processor;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;

import java.util.ArrayList;

public interface GameSessionProcessorCallback {
    public void gameFound(@NonNull final RemoteFoundGameData foundGameData);
    public void errorOccurred(@NonNull final Error error);
    public void messageReceived(@NonNull final RemoteMessage message);
    public void onChattingPhaseIsOver();
    public void onChoosingPhaseIsOver(
            @NonNull final ArrayList<MatchedUserProfileData> userProfileContactDataList);
    public void onDisconnection(final boolean isIncorrect);
}
