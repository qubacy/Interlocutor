package com.qubacy.interlocutor.ui.common.broadcaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

public abstract class BroadcastReceiverBase extends BroadcastReceiver {
    protected final Context m_context;
    protected final BroadcastReceiverBaseCallback m_callback;

    protected BroadcastReceiverBase(
            final Context context,
            final BroadcastReceiverBaseCallback callback)
    {
        m_context = context;
        m_callback = callback;
    }

    public abstract IntentFilter generateIntentFilter();
}
