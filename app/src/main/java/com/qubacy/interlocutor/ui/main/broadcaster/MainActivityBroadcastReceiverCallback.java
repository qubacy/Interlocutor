package com.qubacy.interlocutor.ui.main.broadcaster;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.struct.error.Error;

public interface MainActivityBroadcastReceiverCallback {
    public void onErrorOccurred(@NonNull final Error error);
}
