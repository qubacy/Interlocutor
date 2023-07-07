package com.qubacy.interlocutor.ui.screen.play.choosing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.utility.time.TimeUtility;
import com.qubacy.interlocutor.ui.screen.play.PlayFragment;
import com.qubacy.interlocutor.ui.screen.play.choosing.model.PlayChoosingFragmentViewModel;
import com.qubacy.interlocutor.ui.screen.play.choosing.model.PlayChoosingViewModel;
import com.qubacy.interlocutor.ui.screen.play.common.task.TextViewTimerAsyncTask;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public class PlayChoosingFragment extends PlayFragment {

    private PlayChoosingViewModel m_playChoosingViewModel = null;
    private PlayChoosingFragmentViewModel m_playChoosingFragmentViewModel = null;

    private Button m_confirmButton = null;

    private TextViewTimerAsyncTask m_timerAsyncTask = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_playChoosingViewModel =
                (PlayChoosingViewModel) new ViewModelProvider(getActivity()).
                        get(PlayFullViewModel.class);
        m_playChoosingFragmentViewModel =
                new ViewModelProvider(this).get(PlayChoosingFragmentViewModel.class);


        // todo: registering a broadcast receiver..


        if (m_playChoosingFragmentViewModel.getRemainingTime() == null) {
            m_playChoosingFragmentViewModel.setRemainingTime(
                    m_playChoosingViewModel.getChoosingDuration());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_play_choosing, container, false);

        TextView timerTextView = view.findViewById(R.id.play_choosing_header_timer);

        timerTextView.setText(
                TimeUtility.millisecondsToMinutesString(
                        m_playChoosingFragmentViewModel.getRemainingTime()));

        m_timerAsyncTask =
                TextViewTimerAsyncTask.getInstance(m_playChoosingFragmentViewModel, timerTextView);
        m_confirmButton = view.findViewById(R.id.play_choosing_button_confirm);

        m_confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmClicked();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivityUtility.setAppCompatActivityActionBarTitle(
                getActivity(), R.string.play_choosing_fragment_status_bar_title);

        m_timerAsyncTask.execute();
    }

    @Override
    public void onDestroy() {
        if (m_timerAsyncTask != null)
            m_timerAsyncTask.cancel(true);

        // todo: unregistering a broadcast receiver..

        super.onDestroy();
    }

    private void onConfirmClicked() {
        m_confirmButton.setEnabled(false);

        // todo: sending a list of chosen users..
    }
}
