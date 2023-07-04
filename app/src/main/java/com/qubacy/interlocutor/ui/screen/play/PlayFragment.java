package com.qubacy.interlocutor.ui.screen.play;

import androidx.appcompat.app.AppCompatActivity;

import com.qubacy.interlocutor.ui.screen.FragmentBase;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public abstract class PlayFragment extends FragmentBase {
    protected void closeGame() {
        AppCompatActivity activity =
                ActivityUtility.getAppCompatActivityByActivity(getActivity());

        if (activity == null) return;

        activity.finish();
    }
}
