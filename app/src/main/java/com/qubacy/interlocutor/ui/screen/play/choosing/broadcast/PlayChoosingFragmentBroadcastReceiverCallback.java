package com.qubacy.interlocutor.ui.screen.play.choosing.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBaseCallback;

import java.util.List;

public interface PlayChoosingFragmentBroadcastReceiverCallback
        extends BroadcastReceiverBaseCallback
{
    public void onTimeIsOver(
            @NonNull final List<MatchedUserProfileData> userIdContactDataList);
}
