package com.qubacy.interlocutor.ui.common.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.ui.common.activity.broadcaster.ErrorHandlingBroadcastReceiverCallback;

public abstract class ErrorHandlingActivity extends ActivityBase
    implements
        ErrorHandlingBroadcastReceiverCallback
{
    protected void showErrorToast(final Error error) {
        if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED)
            return;

        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
