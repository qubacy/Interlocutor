package com.qubacy.interlocutor.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiverCallback;
import com.qubacy.interlocutor.ui.main.error.MainActivityErrorEnum;

public class MainActivity extends AppCompatActivity
    implements
        MainActivityBroadcastReceiverCallback
{
    private MainActivityBroadcastReceiver m_broadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MainActivityBroadcastReceiver mainActivityBroadcastReceiver =
                MainActivityBroadcastReceiver.start(this, this);

        if (mainActivityBroadcastReceiver == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    this,
                    MainActivityErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.getResourceCode(),
                    MainActivityErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.isCritical());

            onErrorOccurred(error);

            return;
        }

        m_broadcastReceiver = mainActivityBroadcastReceiver;
    }

    @Override
    protected void onDestroy() {
        MainActivityBroadcastReceiver.stop(this, m_broadcastReceiver);

        super.onDestroy();
    }

    @Override
    public void onErrorOccurred(@NonNull final Error error) {
        // todo: processing the error...
    }
}