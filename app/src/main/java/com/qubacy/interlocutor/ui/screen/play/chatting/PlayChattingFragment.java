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
import androidx.navigation.Navigation;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.service.broadcast.GameServiceBroadcastReceiver;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.PlayFragment;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.PlayChattingFragmentBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.chatting.broadcast.PlayChattingFragmentBroadcastReceiverCallback;
import com.qubacy.interlocutor.ui.screen.play.chatting.error.PlayChattingFragmentErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.chatting.model.PlayChattingViewModel;
import com.qubacy.interlocutor.ui.screen.play.model.PlayViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public class PlayChattingFragment extends PlayFragment
    implements
        PlayChattingFragmentBroadcastReceiverCallback
{
    private PlayChattingViewModel m_viewModel = null;
    private PlayChattingFragmentBroadcastReceiver m_broadcastReceiver = null;

    private EditText m_messageEditText = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_viewModel =
                (PlayChattingViewModel) new ViewModelProvider(getActivity()).
                        get(PlayViewModel.class);

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
        m_viewModel.addMessage(message);

        // todo: notifying the messages' list of the change..


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

        Message message = Message.getInstance(messageText);

        if (message == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingFragmentErrorEnum.SENDING_MESSAGE_CREATION_FAILED.getResourceCode(),
                    PlayChattingFragmentErrorEnum.SENDING_MESSAGE_CREATION_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(m_context, error);

            return;
        }

        GameServiceBroadcastReceiver.broadcastSendMessage(m_context, message);
    }
}