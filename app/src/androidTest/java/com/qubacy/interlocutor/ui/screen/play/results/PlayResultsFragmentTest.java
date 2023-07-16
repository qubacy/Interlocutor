package com.qubacy.interlocutor.ui.screen.play.results;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.results.MatchedUserProfileData;
import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;
import com.qubacy.interlocutor.ui.utility.RecyclerViewUtility;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class PlayResultsFragmentTest {
    private final static String C_CONTACT_COMMON_PART = "contact of User ";

    private Context m_context = null;
    private FragmentScenario<PlayResultsFragment> m_fragmentScenario = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        m_fragmentScenario =
                FragmentScenario.launchInContainer(
                        PlayResultsFragment.class,
                        null,
                        R.style.Theme_Interlocutor,
                        Lifecycle.State.CREATED);
    }

    private void initFullViewModelWithNMatchedUsers(
            final int[] matchedUserIdArray)
    {
        m_fragmentScenario.onFragment(new FragmentScenario.FragmentAction<PlayResultsFragment>() {
            @Override
            public void perform(@NonNull final PlayResultsFragment playResultsFragment) {
                PlayFullViewModel playFullViewModel =
                        new ViewModelProvider(playResultsFragment.getActivity()).
                                get(PlayFullViewModel.class);

                List<ProfilePublic> profileList =
                        new ArrayList<ProfilePublic>() {
                            {
                                for (final int matchedUserId : matchedUserIdArray)
                                    add(ProfilePublic.getInstance(
                                            matchedUserId, "user" + matchedUserId));
                            }
                        };

                playFullViewModel.setFoundGameData(FoundGameData.getInstance(
                        0,
                        System.currentTimeMillis(),
                        1000,
                        5000,
                        "some topic",
                        profileList));

                playFullViewModel.setUserIdContactDataList(new ArrayList<MatchedUserProfileData>() {
                    {
                        for (final int matchedUserIndex : matchedUserIdArray)
                            add(MatchedUserProfileData.getInstance(
                                    matchedUserIndex, C_CONTACT_COMMON_PART + matchedUserIndex));
                    }
                });
            }
        });
    }

    private ClipboardManager getClipboardManager() {
        final ClipboardManager[] clipboardManagerWrapper = {null};

        m_fragmentScenario.onFragment(new FragmentScenario.FragmentAction<PlayResultsFragment>() {
            @Override
            public void perform(@NonNull PlayResultsFragment playResultsFragment) {
                clipboardManagerWrapper[0] =
                        (ClipboardManager) playResultsFragment.getContext().
                                getSystemService(Context.CLIPBOARD_SERVICE);
            }
        });

        return clipboardManagerWrapper[0];
    }

    @Test
    public void testClipboardByCopyingUser2ContactHaving2Users() {
        final int chosenUserId = 2;

        initFullViewModelWithNMatchedUsers(new int [] {1, 2});

        ClipboardManager clipboardManager = getClipboardManager();

        m_fragmentScenario.moveToState(Lifecycle.State.RESUMED);

        Espresso.
                onView(ViewMatchers.withId(R.id.play_results_user_contact_list)).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(2))).
                perform(UserContactListElementClickViewAction.getInstance(chosenUserId - 1));

        Assert.assertTrue(clipboardManager.hasPrimaryClip());

        final int clipItemCount = clipboardManager.getPrimaryClip().getItemCount();

        Assert.assertFalse(clipItemCount <= 0);

        final int lastClipItemIndex = clipItemCount - 1;

        Assert.assertNotNull(
                clipboardManager.getPrimaryClip().getItemAt(lastClipItemIndex).toString());
        Assert.assertEquals(
                C_CONTACT_COMMON_PART + chosenUserId,
                clipboardManager.getPrimaryClip().getItemAt(lastClipItemIndex).getText());
    }

    @Test
    public void testClipboardByCopyingUser2ContactThenCopyingUser1ContactHaving2Users() {
        final int chosenUserId = 2;

        initFullViewModelWithNMatchedUsers(new int [] {1, 2});

        ClipboardManager clipboardManager = getClipboardManager();

        m_fragmentScenario.moveToState(Lifecycle.State.RESUMED);

        Espresso.
                onView(ViewMatchers.withId(R.id.play_results_user_contact_list)).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(2))).
                perform(UserContactListElementClickViewAction.getInstance(chosenUserId - 1)).
                perform(UserContactListElementClickViewAction.getInstance(0));

        Assert.assertTrue(clipboardManager.hasPrimaryClip());

        final int clipItemCount = clipboardManager.getPrimaryClip().getItemCount();

        Assert.assertFalse(clipItemCount <= 0);

        final int lastClipItemIndex = clipItemCount - 1;

        Assert.assertNotNull(
                clipboardManager.getPrimaryClip().getItemAt(lastClipItemIndex).toString());
        Assert.assertEquals(
                C_CONTACT_COMMON_PART + 1,
                clipboardManager.getPrimaryClip().getItemAt(lastClipItemIndex).getText());
    }

    private static class UserContactListElementClickViewAction implements ViewAction {
        private final int m_position;

        protected UserContactListElementClickViewAction(final int position) {
            m_position = position;
        }

        public static UserContactListElementClickViewAction getInstance(
                final int position)
        {
            if (position < 0) return null;

            return new UserContactListElementClickViewAction(position);
        }

        @Override
        public String getDescription() {
            return "";
        }

        @Override
        public Matcher<View> getConstraints() {
            return ViewMatchers.withId(R.id.play_results_user_contact_list);
        }

        @Override
        public void perform(
                final UiController uiController,
                final View view)
        {
            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.ViewHolder viewHolder =
                    RecyclerViewUtility.getViewHolderByPosition(recyclerView, m_position);

            if (viewHolder == null) throw new IllegalStateException();

            ViewActions.click().perform(uiController, viewHolder.itemView);
        }
    }
}
