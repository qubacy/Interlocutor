package com.qubacy.interlocutor.ui.main.broadcaster;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;

public interface MainActivityBroadcastReceiverCallback {
    public void onErrorOccurred(@NonNull final Error error);
}
