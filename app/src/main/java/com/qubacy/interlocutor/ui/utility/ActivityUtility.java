package com.qubacy.interlocutor.ui.utility;

import android.app.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityUtility {
    public static AppCompatActivity getAppCompatActivityByActivity(
            final Activity activity)
    {
        if (activity == null) return null;
        if (!(activity instanceof AppCompatActivity)) return null;

        return ((AppCompatActivity) activity);
    }

    public static ActionBar getAppCompatActivityActionBar(final Activity activity) {
        AppCompatActivity appCompatActivity = getAppCompatActivityByActivity(activity);

        if (appCompatActivity == null) return null;

        return appCompatActivity.getSupportActionBar();
    }

    public static boolean setAppCompatActivityActionBarTitle(
            final Activity activity,
            final int stringResId)
    {
        ActionBar actionBar = getAppCompatActivityActionBar(activity);

        if (actionBar == null) return false;

        actionBar.setTitle(stringResId);
        actionBar.show();

        return true;
    }

    public static boolean hideAppCompatActivityActionBar(final Activity activity) {
        ActionBar actionBar = getAppCompatActivityActionBar(activity);

        if (actionBar == null) return false;

        actionBar.hide();

        return true;
    }
}
