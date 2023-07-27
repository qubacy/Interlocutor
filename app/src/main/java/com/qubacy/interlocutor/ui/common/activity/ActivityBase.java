package com.qubacy.interlocutor.ui.common.activity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;

public abstract class ActivityBase extends AppCompatActivity {
    public static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";
    public static final String C_AD_UNIT_ID = "demo-banner-yandex";

    protected BannerAdView m_bannerAdView = null;

    protected boolean setCustomActionBar(final int actionBarResId) {
        Toolbar toolbar = findViewById(actionBarResId);

        if (toolbar == null) return false;

        setSupportActionBar(toolbar);

        return true;
    }

    protected boolean setBannerAd(
            final Context context,
            final int bannerResId)
    {
        if (context == null) return false;

        BannerAdView bannerAdView = findViewById(bannerResId);

        if (bannerAdView == null) return false;

        m_bannerAdView = bannerAdView;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float widthInDp =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    displayMetrics.widthPixels,
                    displayMetrics);

        m_bannerAdView.setAdUnitId(C_AD_UNIT_ID);
        m_bannerAdView.setAdSize(AdSize.stickySize(this, (int) widthInDp));

        final AdRequest adRequest = new AdRequest.Builder().build();

        m_bannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {
                Log.d(YANDEX_MOBILE_ADS_TAG, "Ad has been loaded!");
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                Log.d(YANDEX_MOBILE_ADS_TAG, adRequestError.getDescription());
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        m_bannerAdView.loadAd(adRequest);

        return true;
    }
}
