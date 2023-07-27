package com.qubacy.interlocutor.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.common.activity.ErrorHandlingActivity;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiverCallback;
import com.qubacy.interlocutor.ui.main.error.ErrorFragment;
import com.qubacy.interlocutor.ui.main.error.MainActivityErrorEnum;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

public class MainActivity extends ErrorHandlingActivity
    implements
        MainActivityBroadcastReceiverCallback
{
    private static final String C_ERROR_TAG = "error";

    private MainActivityBroadcastReceiver m_broadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setCustomActionBar(R.id.activity_main_toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) actionBar.hide();

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

        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");
            }
        });

        if (!setBannerAd(this, R.id.activity_main_banner_ad_view)) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            this,
                            MainActivityErrorEnum.AD_BANNER_LOADING_FAILED.getResourceCode(),
                            MainActivityErrorEnum.AD_BANNER_LOADING_FAILED.isCritical());

            onErrorOccurred(error);

            return;
        }
    }

    @Override
    protected void onDestroy() {
        MainActivityBroadcastReceiver.stop(this, m_broadcastReceiver);

        super.onDestroy();
    }

    @Override
    public void onErrorOccurred(
            @NonNull final Error error)
    {
        if (error.isCritical()) {
            showCriticalError(error);

            return;
        }

        showErrorToast(error);
    }

    private void showCriticalError(final Error error) {
        ErrorFragment errorFragment = ErrorFragment.getInstance(error);

        if (errorFragment == null) {
            finishAndRemoveTask();

            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.
                beginTransaction().
                replace(R.id.fragmentContainerView, errorFragment, C_ERROR_TAG).
                addToBackStack(null).
                commitAllowingStateLoss();

        Intent intent = new Intent(this, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(C_ERROR_TAG);

        if (fragment != null) {
            finishAndRemoveTask();

            return;
        }

        super.onBackPressed();
    }
}