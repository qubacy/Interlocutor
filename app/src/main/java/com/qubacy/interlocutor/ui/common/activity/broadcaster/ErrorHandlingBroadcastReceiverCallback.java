package com.qubacy.interlocutor.ui.common.activity.broadcaster;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.ui.common.broadcaster.BroadcastReceiverBaseCallback;

public interface ErrorHandlingBroadcastReceiverCallback extends BroadcastReceiverBaseCallback {
    public void onErrorOccurred(@NonNull final Error error);
}
