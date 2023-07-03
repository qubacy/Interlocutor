package com.qubacy.interlocutor.ui.screen.play;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.qubacy.interlocutor.ui.screen.FragmentBase;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public abstract class PlayFragment extends FragmentBase {
    protected void closeGame(final View view) {
        AppCompatActivity activity =
                ActivityUtility.getAppCompatActivityByActivity(getActivity());

        if (activity == null) return;

        activity.finish();
    }
}
