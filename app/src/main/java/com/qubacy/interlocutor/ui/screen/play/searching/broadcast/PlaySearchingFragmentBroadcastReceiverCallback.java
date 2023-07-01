package com.qubacy.interlocutor.ui.screen.play.searching.broadcast;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;

public interface PlaySearchingFragmentBroadcastReceiverCallback {
    public void onGameFound(@NonNull final FoundGameData foundGameData);
    public void onSearchingStopped();
}
