package com.qubacy.interlocutor.ui.screen.play.main.broadcaster;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.qubacy.interlocutor.ui.common.activity.broadcaster.ErrorHandlingBroadcastReceiver;

public class PlayActivityBroadcastReceiver extends ErrorHandlingBroadcastReceiver {
    protected PlayActivityBroadcastReceiver(
            final Context context,
            final PlayActivityBroadcastReceiverCallback callback)
    {
        super(context, callback);
    }

    public static PlayActivityBroadcastReceiver getInstance(
            final Context context,
            final PlayActivityBroadcastReceiverCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayActivityBroadcastReceiver(context, callback);
    }

    public static PlayActivityBroadcastReceiver start(
            final Context context,
            final PlayActivityBroadcastReceiverCallback callback)
    {
        PlayActivityBroadcastReceiver broadcastReceiver =
                PlayActivityBroadcastReceiver.getInstance(context, callback);

        IntentFilter intentFilter = broadcastReceiver.generateIntentFilter();

        LocalBroadcastManager.
                getInstance(context).
                registerReceiver(broadcastReceiver, intentFilter);

        return broadcastReceiver;
    }

    public static void stop(
            final Context context,
            final PlayActivityBroadcastReceiver broadcastReceiver)
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public IntentFilter generateIntentFilter() {
        IntentFilter intentFilter = super.generateIntentFilter();

        return intentFilter;
    }

    @Override
    public void onReceive(
            final Context context,
            final Intent intent)
    {
        super.onReceive(context, intent);
    }
}
