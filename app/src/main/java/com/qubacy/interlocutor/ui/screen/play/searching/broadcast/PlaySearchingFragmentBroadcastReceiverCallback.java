package com.qubacy.interlocutor.ui.screen.play.searching.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBaseCallback;

public interface PlaySearchingFragmentBroadcastReceiverCallback
        extends BroadcastReceiverBaseCallback
{
    public void onServiceReady();
    public void onGameFound(@NonNull final FoundGameData foundGameData);
}
