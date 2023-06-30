package com.qubacy.interlocutor.data.game.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.struct.message.Message;
import com.qubacy.interlocutor.data.general.struct.profile.local.Profile;
import com.qubacy.interlocutor.data.general.struct.profile.other.OtherProfilePublic;

import java.util.List;

public interface GameServiceBroadcastReceiverCallback {
    public void onStartSearchingRequested(
            @NonNull final Profile profile);
    public void onStopSearchingRequested();
    public void onMessageSendingRequested(
            @NonNull final Message message);
    public void onChooseUsersRequested(
            @NonNull final List<OtherProfilePublic> chosenUserList);
    public void onLeaveRequested();
}
