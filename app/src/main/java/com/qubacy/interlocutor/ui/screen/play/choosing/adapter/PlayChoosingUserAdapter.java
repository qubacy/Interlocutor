package com.qubacy.interlocutor.ui.screen.play.choosing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.choosing.adapter.error.PlayChoosingUserAdapterErrorEnum;

public class PlayChoosingUserAdapter extends RecyclerView.Adapter<PlayChoosingUserViewHolder>
    implements
        PlayChoosingUserViewHolderCallback
{
    private final Context m_context;
    private final LayoutInflater m_layoutInflater;
    private final PlayChoosingUserAdapterCallback m_callback;

    protected PlayChoosingUserAdapter(
            final Context context,
            final PlayChoosingUserAdapterCallback callback)
    {
        m_context = context;
        m_layoutInflater = LayoutInflater.from(context);
        m_callback = callback;
    }

    public static PlayChoosingUserAdapter getInstance(
            final Context context,
            final PlayChoosingUserAdapterCallback callback)
    {
        if (context == null || callback == null)
            return null;

        return new PlayChoosingUserAdapter(context, callback);
    }

    @NonNull
    @Override
    public PlayChoosingUserViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType)
    {
        View view =
                m_layoutInflater.inflate(
                        R.layout.fragment_play_choosing_user, parent, false);

        return new PlayChoosingUserViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(
            @NonNull final PlayChoosingUserViewHolder holder,
            final int position)
    {
        ProfilePublic profile = m_callback.getProfileByIndex(position);

        if (profile == null) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            m_context,
                            PlayChoosingUserAdapterErrorEnum.NULL_PROFILE_DATA.getResourceCode(),
                            PlayChoosingUserAdapterErrorEnum.NULL_PROFILE_DATA.isCritical());

            m_callback.onUserAdapterErrorOccurred(error);

            return;
        }

        if (!holder.setData(
                profile,
                m_callback.isUserChosenById(profile.getId()),
                m_callback.isChoosingEnabled()))
        {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                        m_context,
                        PlayChoosingUserAdapterErrorEnum.SETTING_DATA_FAILED.getResourceCode(),
                        PlayChoosingUserAdapterErrorEnum.SETTING_DATA_FAILED.isCritical());

            m_callback.onUserAdapterErrorOccurred(error);

            return;
        }
    }

    @Override
    public int getItemCount() {
        return m_callback.getUserCount();
    }

    @Override
    public void onUserChoosingStateChange(
            @NonNull final ProfilePublic profilePublic,
            final boolean isChosen)
    {
        boolean result = false;

        if (isChosen)
            result = m_callback.addChosenUserId(profilePublic.getId());
        else
            result = m_callback.removeChosenUserId(profilePublic.getId());

        if (!result) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    m_context,
                    PlayChoosingUserAdapterErrorEnum.USER_CHOSEN_STATE_SETTING_FAILED.getResourceCode(),
                    PlayChoosingUserAdapterErrorEnum.USER_CHOSEN_STATE_SETTING_FAILED.isCritical());

            m_callback.onUserAdapterErrorOccurred(error);

            return;
        }
    }
}
