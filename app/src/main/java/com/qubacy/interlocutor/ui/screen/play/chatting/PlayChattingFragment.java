package com.qubacy.interlocutor.ui.screen.play.chatting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.PlayFragment;
import com.qubacy.interlocutor.ui.screen.play.chatting.adapter.PlayChattingMessageAdapter;
import com.qubacy.interlocutor.ui.screen.play.chatting.adapter.PlayChattingMessageAdapterCallback;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.PlayChattingFragmentBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.PlayChattingFragmentBroadcastReceiverCallback;
import com.qubacy.interlocutor.ui.screen.play.chatting.error.PlayChattingFragmentErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.chatting.model.PlayChattingFragmentViewModel;
import com.qubacy.interlocutor.ui.screen.play.chatting.model.PlayChattingViewModel;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public class PlayChattingFragment extends PlayFragment
    implements
        PlayChattingFragmentBroadcastReceiverCallback,
        PlayChattingMessageAdapterCallback
{
    private PlayChattingFragmentViewModel m_playChattingFragmentViewModel = null;
    private PlayChattingViewModel m_playChattingViewModel = null;

    private PlayChattingMessageAdapter m_playChattingMessageAdapter = null;

    private PlayChattingFragmentBroadcastReceiver m_broadcastReceiver = null;

    private EditText m_messageEditText = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_playChattingFragmentViewModel =
                new ViewModelProvider(this).get(PlayChattingFragmentViewModel.class);
        m_playChattingViewModel =
                (PlayChattingViewModel) new ViewModelProvider(getActivity()).
                        get(PlayFullViewModel.class);

        PlayChattingFragmentBroadcastReceiver broadcastReceiver =
                PlayChattingFragmentBroadcastReceiver.start(m_context, this);

        if (broadcastReceiver == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.getResourceCode(),
                    PlayChattingFragmentErrorEnum.BROADCAST_RECEIVER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        m_broadcastReceiver = broadcastReceiver;
    }

    @Override
    public void onDestroy() {
        PlayChattingFragmentBroadcastReceiver.stop(m_context, m_broadcastReceiver);

        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_play_chatting, container, false);

        PlayChattingMessageAdapter playChattingMessageAdapter =
                PlayChattingMessageAdapter.getInstance(m_context, this);

        if (playChattingMessageAdapter == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentErrorEnum.MESSAGE_ADAPTER_CREATION_FAILED.getResourceCode(),
                    PlayChattingFragmentErrorEnum.MESSAGE_ADAPTER_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return view;
        }

        m_playChattingMessageAdapter = playChattingMessageAdapter;

        RecyclerView recyclerView = view.findViewById(R.id.play_chatting_chat_list);

        recyclerView.setAdapter(m_playChattingMessageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(m_context));

        m_messageEditText = view.findViewById(R.id.play_chatting_section_sending_message_text);

        Button sendButton = view.findViewById(R.id.play_chatting_section_sending_button_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMessageSendingRequested();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivityUtility.setAppCompatActivityActionBarTitle(
                getActivity(), R.string.play_chatting_fragment_status_bar_title);
    }

    @Override
    public void onMessageReceived(@NonNull final Message message) {
        int lastItemIndex = m_playChattingFragmentViewModel.getMessageCount() - 1;

        m_playChattingFragmentViewModel.addMessage(message);
        m_playChattingMessageAdapter.notifyItemRangeInserted(lastItemIndex, 1);
    }

    @Override
    public void onTimeIsOver() {
        // todo: moving to the PlayChoosingFragment screen..

//        Navigation.
//                findNavController(getView()).
//                navigate(R.id.);
    }

    private void onMessageSendingRequested() {
        String messageText = m_messageEditText.getText().toString();

        if (messageText.isEmpty()) {
            // todo: should I notify user about it?

            return;
        }

        if (!m_playChattingFragmentViewModel.onMessageSendingRequested(messageText, m_context)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentErrorEnum.SENDING_MESSAGE_CREATION_FAILED.getResourceCode(),
                    PlayChattingFragmentErrorEnum.SENDING_MESSAGE_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }
    }

    @Override
    public Message getMessageByIndex(final int index) {
        return m_playChattingFragmentViewModel.getMessageByIndex(index);
    }

    @Override
    public ProfilePublic getSenderProfileById(final int id) {
        return m_playChattingViewModel.getProfileById(id);
    }

    @Override
    public int getMessageCount() {
        return m_playChattingFragmentViewModel.getMessageCount();
    }

    @Override
    public void onMessageAdapterErrorOccurred(@NonNull final Error error) {
        MainActivityBroadcastReceiver.broadcastError(m_context, error);
    }
}
