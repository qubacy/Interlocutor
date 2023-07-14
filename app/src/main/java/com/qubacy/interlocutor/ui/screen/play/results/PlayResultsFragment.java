package com.qubacy.interlocutor.ui.screen.play.results;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.PlayFragment;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;
import com.qubacy.interlocutor.ui.screen.play.results.adapter.PlayResultsUserContactAdapter;
import com.qubacy.interlocutor.ui.screen.play.results.adapter.PlayResultsUserContactAdapterCallback;
import com.qubacy.interlocutor.ui.screen.play.results.model.PlayResultsViewModel;
import com.qubacy.interlocutor.ui.utility.ActivityUtility;

public class PlayResultsFragment extends PlayFragment
    implements
        PlayResultsUserContactAdapterCallback
{

    private PlayResultsViewModel m_playResultsViewModel = null;
    private ClipboardManager m_clipboardManager = null;

    private PlayResultsUserContactAdapter m_adapter = null;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_playResultsViewModel =
                (PlayResultsViewModel) new ViewModelProvider(getActivity()).
                        get(PlayFullViewModel.class);

        m_clipboardManager =
                (ClipboardManager) m_context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_play_results, container, false);

        RecyclerView userContactList = view.findViewById(R.id.play_results_user_contact_list);

        PlayResultsUserContactAdapter adapter =
                PlayResultsUserContactAdapter.getInstance(m_context, this);

        if (adapter == null) {
            // todo: processing an error..

            return view;
        }

        m_adapter = adapter;

        userContactList.setAdapter(m_adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(m_context);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(m_context, linearLayoutManager.getOrientation());

        userContactList.setLayoutManager(linearLayoutManager);
        userContactList.addItemDecoration(itemDecoration);

        Button finishButton = view.findViewById(R.id.play_results_button_finish);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonFinishClicked();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivityUtility.setAppCompatActivityActionBarTitle(
                getActivity(), R.string.play_results_fragment_status_bar_title);
    }

    private void onButtonFinishClicked() {
        closeGame();
    }

    @Override
    public MatchedUserProfileData getUserProfileDataByIndex(final int index) {
        return m_playResultsViewModel.getMatchedUserProfileDataByIndex(index);
    }

    @Override
    public ProfilePublic getProfileById(final int profileId) {
        return m_playResultsViewModel.getProfileById(profileId);
    }

    @Override
    public int getUserProfileContactDataCount() {
        return m_playResultsViewModel.getMatchedUserProfileDataCount();
    }

    @Override
    public void copyContactToClipboard(final String contact) {
        ClipData clip = ClipData.newPlainText(contact, contact);

        m_clipboardManager.setPrimaryClip(clip);

        Toast.
                makeText(
                        m_context,
                        R.string.play_results_fragment_clipboard_contact_copied_note,
                        Toast.LENGTH_SHORT).
                show();
    }
}
