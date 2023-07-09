package com.qubacy.interlocutor.data.game.internal.service.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;

import java.util.List;

public interface GameServiceBroadcastReceiverCallback {
    public void onStartSearchingRequested(
            @NonNull final Profile profile);
    public void onStopSearchingRequested();
    public void onMessageSendingRequested(
            @NonNull final Message message);
    public void onChooseUsersRequested(
            @NonNull final List<Integer> chosenUserIdList);
    public void onLeaveRequested();
}
