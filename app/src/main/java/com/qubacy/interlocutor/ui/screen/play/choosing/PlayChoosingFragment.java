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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.data.general.export.utility.time.TimeUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.PlayFragment;
import com.qubacy.interlocutor.ui.screen.play.choosing.adapter.PlayChoosingUserAdapter;
import com.qubacy.interlocutor.ui.screen.play.choosing.adapter.PlayChoosingUserAdapterCallback;
import com.qubacy.interlocutor.ui.screen.play.choosing.broadcast.PlayChoosingFragmentBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.choosing.broadcast.PlayChoosingFragmentBroadcastReceiverCallback;
import com.qubacy.interlocutor.ui.screen.play.choosing.error.PlayChoosingFragmentErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.choosing.model.PlayChoosingFragmentViewModel;
import com.qubacy.interlocutor.ui.screen.play.choosing.model.PlayChoosingViewModel;
import com.qubacy.interlocutor.ui.screen.play.common.task.TextViewTimerAsyncTask;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

import java.util.List;

public class PlayChoosingFragment extends PlayFragment
    implements
        PlayChoosingUserAdapterCallback,
        PlayChoosingFragmentBroadcastReceiverCallback
{

    private PlayChoosingViewModel m_playChoosingViewModel = null;
    private PlayChoosingFragmentViewModel m_playChoosingFragmentViewModel = null;

    private PlayChoosingFragmentBroadcastReceiver m_broadcastReceiver = null;
    private PlayChoosingUserAdapter m_adapter = null;
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

        PlayChoosingFragmentBroadcastReceiver broadcastReceiver =
                PlayChoosingFragmentBroadcastReceiver.start(m_context, this);

        if (broadcastReceiver == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChoosingFragmentErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.getResourceCode(),
                    PlayChoosingFragmentErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        m_broadcastReceiver = broadcastReceiver;

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

        PlayChoosingUserAdapter playChoosingUserAdapter =
                PlayChoosingUserAdapter.getInstance(m_context, this);

        if (playChoosingUserAdapter == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChoosingFragmentErrorEnum.USER_ADAPTER_CREATION_FAILED.getResourceCode(),
                    PlayChoosingFragmentErrorEnum.USER_ADAPTER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);


            return view;
        }

        m_adapter = playChoosingUserAdapter;

        RecyclerView playChoosingUserRecyclerView =
                view.findViewById(R.id.play_choosing_user_list);

        playChoosingUserRecyclerView.setAdapter(m_adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(m_context);

        playChoosingUserRecyclerView.setLayoutManager(linearLayoutManager);

        m_confirmButton = view.findViewById(R.id.play_choosing_button_confirm);

        if (!m_playChoosingFragmentViewModel.isChoiceMade()) {
            m_confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onConfirmClicked();
                }
            });
        } else
            m_confirmButton.setEnabled(false);

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

        PlayChoosingFragmentBroadcastReceiver.stop(m_context, m_broadcastReceiver);

        super.onDestroy();
    }

    private void onConfirmClicked() {
        m_playChoosingFragmentViewModel.setChoiceMade(true);
        m_adapter.notifyDataSetChanged();
        m_confirmButton.setEnabled(false);

        m_playChoosingFragmentViewModel.confirmChosenUsers(m_context);
    }

    @Override
    public int getUserCount() {
        return m_playChoosingViewModel.getUserCount();
    }

    @Override
    public ProfilePublic getProfileByIndex(final int index) {
        return m_playChoosingViewModel.getProfileByIndex(index);
    }

    @Override
    public boolean isUserChosenById(final int userId) {
        return m_playChoosingFragmentViewModel.isUserChosenById(userId);
    }

    @Override
    public boolean addChosenUserId(final int userId) {
        return m_playChoosingFragmentViewModel.addChosenUserId(userId);
    }

    @Override
    public boolean removeChosenUserId(final int userId) {
        return m_playChoosingFragmentViewModel.removeChosenUserId(userId);
    }

    @Override
    public boolean isChoosingEnabled() {
        return (!m_playChoosingFragmentViewModel.isChoiceMade());
    }

    @Override
    public void onUserAdapterErrorOccurred(@NonNull final Error error) {
        MainActivityBroadcastReceiver.broadcastError(m_context, error);
    }

    @Override
    public void onTimeIsOver(final List<MatchedUserProfileData> userIdContactDataList) {
        if (!m_playChoosingViewModel.setUserIdContactDataList(userIdContactDataList)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChoosingFragmentErrorEnum.USER_ID_CONTACT_DATA_LIST_SETTING_FAILED.getResourceCode(),
                    PlayChoosingFragmentErrorEnum.USER_ID_CONTACT_DATA_LIST_SETTING_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        Navigation.
                findNavController(getView()).
                navigate(R.id.action_playChoosingFragment_to_playResultsFragment);
    }
}
