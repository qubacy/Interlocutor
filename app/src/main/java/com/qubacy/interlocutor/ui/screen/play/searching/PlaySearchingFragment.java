package com.qubacy.interlocutor.ui.screen.play.searching;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.broadcast.GameServiceBroadcastReceiver;
import com.qubacy.interlocutor.data.game.struct.FoundGameData;
import com.qubacy.interlocutor.data.general.struct.error.Error;
import com.qubacy.interlocutor.data.general.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.PlayFragment;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;
import com.qubacy.interlocutor.ui.screen.play.searching.broadcast.PlaySearchingFragmentBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.searching.broadcast.PlaySearchingFragmentBroadcastReceiverCallback;
import com.qubacy.interlocutor.ui.screen.play.searching.error.PlaySearchingFragmentErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.searching.model.PlaySearchingViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public class PlaySearchingFragment extends PlayFragment
    implements
        PlaySearchingFragmentBroadcastReceiverCallback
{
    private PlaySearchingFragmentBroadcastReceiver m_broadcastReceiver = null;

    private PlaySearchingViewModel m_viewModel = null;

    private TextView m_messageTextView = null;
    private AnimatorSet m_progressIndicatorAnimatorSet = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_viewModel =
                (PlaySearchingViewModel) new ViewModelProvider(getActivity()).
                        get(PlayFullViewModel.class);

        PlaySearchingFragmentBroadcastReceiver broadcastReceiver =
                PlaySearchingFragmentBroadcastReceiver.start(m_context, this);

        if (broadcastReceiver == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlaySearchingFragmentErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.getResourceCode(),
                    PlaySearchingFragmentErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        m_broadcastReceiver = broadcastReceiver;
    }

    @Override
    public void onDestroy() {
        PlaySearchingFragmentBroadcastReceiver.stop(m_context, m_broadcastReceiver);

        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_play_searching, container, false);

        m_messageTextView = view.findViewById(R.id.play_searching_message);
        m_progressIndicatorAnimatorSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        m_context, R.animator.play_searching_progress_bar_animator);

        m_progressIndicatorAnimatorSet.setTarget(
                view.findViewById(R.id.play_searching_progress_indicator));
        m_progressIndicatorAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                m_progressIndicatorAnimatorSet.start();
            }
        });

        Button abortButton = view.findViewById(R.id.play_searching_button_abort);

        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processGameSearchingAbort(v);
            }
        });

        return view;
    }

    private void processGameSearchingAbort(final View view) {
        GameServiceBroadcastReceiver.broadcastStopGameSearching(m_context);

        super.closeGame(view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_progressIndicatorAnimatorSet.start();
        m_messageTextView.setText(R.string.play_searching_fragment_message_searching_game);

        GameServiceBroadcastReceiver.broadcastStartGameSearching(
                m_context, m_viewModel.getProfile());

        ActivityUtility.hideAppCompatActivityActionBar(getActivity());
    }

    @Override
    public void onGameFound(@NonNull final FoundGameData foundGameData) {
        m_viewModel.setFoundGameData(foundGameData);
        m_messageTextView.setText(R.string.play_searching_fragment_message_game_found);

        navigateToChatting();
    }

    private void navigateToChatting() {
        Navigation.
                findNavController(getView()).
                navigate(R.id.action_playSearchingFragment_to_playChattingFragment);
    }

    @Override
    public void onSearchingStopped() {
        // todo: doing something??.
    }
}
