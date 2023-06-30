package com.qubacy.interlocutor.ui.screen.play;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public abstract class PlayFragment extends Fragment {
    protected Context m_context = null;

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);

        m_context = context;
    }

    protected void closeGame(final View view) {
        AppCompatActivity activity =
                ActivityUtility.getAppCompatActivityByActivity(getActivity());

        if (activity == null) return;

        activity.finish();
    }
}
