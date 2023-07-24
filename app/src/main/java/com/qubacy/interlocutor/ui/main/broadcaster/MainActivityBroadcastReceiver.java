package com.qubacy.interlocutor.ui.main.broadcaster;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.ui.common.activity.broadcaster.ErrorHandlingBroadcastReceiver;

public class MainActivityBroadcastReceiver extends ErrorHandlingBroadcastReceiver {
    protected MainActivityBroadcastReceiver(
            final Context context,
            final MainActivityBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static MainActivityBroadcastReceiver getInstance(
            final Context context,
            final MainActivityBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new MainActivityBroadcastReceiver(context, callback);
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = super.generateIntentFilter();

        for (final MainActivityBroadcastCommand command : MainActivityBroadcastCommand.values())
            intentFilter.addAction(command.toString());

        return intentFilter;
    }

    public static MainActivityBroadcastReceiver start(
            @NonNull final Context context,
            @NonNull final MainActivityBroadcastReceiverCallback callback)
    {
        MainActivityBroadcastReceiver mainActivityBroadcastReceiver =
                new MainActivityBroadcastReceiver(context, callback);

        IntentFilter intentFilter = mainActivityBroadcastReceiver.generateIntentFilter();

        LocalBroadcastManager.
                getInstance(context).
                registerReceiver(mainActivityBroadcastReceiver, intentFilter);

        return mainActivityBroadcastReceiver;
    }

    public static void stop(
            @NonNull final Context context,
            @NonNull final MainActivityBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        if (!processBroadcast(context, intent)) return;
    }

    @Override
    protected boolean processBroadcast(Context context, Intent intent) {
        if (super.processBroadcast(context, intent)) return true;

        return true;
    }
}
