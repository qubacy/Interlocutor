package com.qubacy.interlocutor.ui.screen.play.results.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;

public class PlayResultsUserContactAdapter
        extends RecyclerView.Adapter<PlayResultsUserContactViewHolder>
{
    private final Context m_context;
    private final LayoutInflater m_layoutInflater;
    private final PlayResultsUserContactAdapterCallback m_callback;

    protected PlayResultsUserContactAdapter(
            final Context context,
            final PlayResultsUserContactAdapterCallback callback)
    {
        super();

        m_context = context;
        m_layoutInflater = LayoutInflater.from(context);
        m_callback = callback;
    }

    public static PlayResultsUserContactAdapter getInstance(
            final Context context,
            final PlayResultsUserContactAdapterCallback callback)
    {
        if (context == null || callback == null)
            return null;

        return new PlayResultsUserContactAdapter(context, callback);
    }

    @NonNull
    @Override
    public PlayResultsUserContactViewHolder onCreateViewHolder(
            @NonNull final ViewGroup parent,
            final int viewType)
    {
        View view =
                m_layoutInflater.inflate(
                        R.layout.fragment_play_results_user_contact, parent, false);

        return new PlayResultsUserContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull final PlayResultsUserContactViewHolder holder,
            final int position)
    {
        MatchedUserProfileData matchedUserProfileData =
                m_callback.getUserProfileDataByIndex(position);

        if (matchedUserProfileData == null) {
            // todo: processing an error..

            return;
        }

        ProfilePublic profilePublic =
                m_callback.getProfileById(matchedUserProfileData.getId());

        if (profilePublic == null) {
            // todo: processing an error..

            return;
        }

        if (!holder.setData(profilePublic.getUsername(), matchedUserProfileData.getContact())) {
            // todo: processing an error..

            return;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_callback.copyContactToClipboard(matchedUserProfileData.getContact());
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_callback.getUserProfileContactDataCount();
    }
}
