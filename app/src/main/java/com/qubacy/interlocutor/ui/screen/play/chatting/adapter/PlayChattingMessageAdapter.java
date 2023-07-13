package com.qubacy.interlocutor.ui.screen.play.chatting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.message.Message;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.chatting.adapter.error.PlayChattingMessageAdapterErrorEnum;

public class PlayChattingMessageAdapter
        extends RecyclerView.Adapter<PlayChattingMessageViewHolder>
{
    private final Context m_context;
    private final LayoutInflater m_layoutInflater;
    private final PlayChattingMessageAdapterCallback m_callback;

    protected PlayChattingMessageAdapter(
            final Context context,
            final PlayChattingMessageAdapterCallback callback)
    {
        super();

        m_context = context;
        m_layoutInflater = LayoutInflater.from(context);
        m_callback = callback;
    }

    public static PlayChattingMessageAdapter getInstance(
            final Context context,
            final PlayChattingMessageAdapterCallback callback)
    {
        if (context == null || callback == null) return null;

        return new PlayChattingMessageAdapter(context, callback);
    }

    @NonNull
    @Override
    public PlayChattingMessageViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType)
    {
        View view =
                m_layoutInflater.inflate(
                        R.layout.fragment_play_chatting_message, parent, false);

        return new PlayChattingMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull final PlayChattingMessageViewHolder holder,
            final int position)
    {
        Message message = m_callback.getMessageByIndex(position);

        if (message == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingMessageAdapterErrorEnum.NULL_MESSAGE.getResourceCode(),
                    PlayChattingMessageAdapterErrorEnum.NULL_MESSAGE.isCritical());

            m_callback.onMessageAdapterErrorOccurred(error);

            return;
        }

        ProfilePublic senderProfile =
                m_callback.getSenderProfileById(message.getSenderId());

        if (senderProfile == null) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingMessageAdapterErrorEnum.NULL_SENDER_PROFILE.getResourceCode(),
                    PlayChattingMessageAdapterErrorEnum.NULL_SENDER_PROFILE.isCritical());

            m_callback.onMessageAdapterErrorOccurred(error);

            return;
        }

        if (!holder.setData(senderProfile, message)) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChattingMessageAdapterErrorEnum.SETTING_VIEW_HOLDER_DATA_FAILED.getResourceCode(),
                    PlayChattingMessageAdapterErrorEnum.SETTING_VIEW_HOLDER_DATA_FAILED.isCritical());

            m_callback.onMessageAdapterErrorOccurred(error);

            return;
        }
    }

    @Override
    public int getItemCount() {
        return m_callback.getMessageCount();
    }
}
