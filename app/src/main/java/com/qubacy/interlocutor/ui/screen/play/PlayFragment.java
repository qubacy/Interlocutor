package com.qubacy.interlocutor.ui.screen.play;

import androidx.appcompat.app.AppCompatActivity;

import com.qubacy.interlocutor.ui.screen.NavigationFragment;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public abstract class PlayFragment extends NavigationFragment {
    protected void closeGame() {
        AppCompatActivity activity =
                ActivityUtility.getAppCompatActivityByActivity(getActivity());

        if (activity == null) return;

        activity.finish();
    }
}
