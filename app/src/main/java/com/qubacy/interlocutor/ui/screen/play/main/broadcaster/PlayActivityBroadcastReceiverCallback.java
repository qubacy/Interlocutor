package com.qubacy.interlocutor.ui.screen.play.main.broadcaster;

import com.qubacy.interlocutor.ui.common.activity.broadcaster.ErrorHandlingBroadcastReceiverCallback;

public interface PlayActivityBroadcastReceiverCallback
        extends ErrorHandlingBroadcastReceiverCallback
{
    public void onUnexpectedDisconnectionOccurred();
}
