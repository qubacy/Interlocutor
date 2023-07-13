package com.qubacy.interlocutor.ui.screen.play.choosing;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.action.MotionEvents;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.profile.ProfilePublic;
import com.qubacy.interlocutor.ui.screen.play.choosing.adapter.PlayChoosingUserViewHolder;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class PlayChoosingFragmentTest {
    private Context m_context = null;
    private FragmentScenario<PlayChoosingFragment> m_fragmentScenario = null;

    @Before
    public void setUp() {
        m_context =
                InstrumentationRegistry.getInstrumentation().getTargetContext();
        m_fragmentScenario =
            FragmentScenario.launchInContainer(
                PlayChoosingFragment.class,
                null,
                R.style.Theme_Interlocutor,
                Lifecycle.State.CREATED);
    }

    private void setUpPlayFullViewModel(
            final int userCount,
            final long choosingStageDuration)
    {
        List<ProfilePublic> userList =
                new ArrayList<ProfilePublic>()
                {
                    {
                        for (int i = 0; i < userCount; ++i)
                            add(ProfilePublic.
                                    getInstance(1, "user" + String.valueOf(i + 1)));
                    }
                };
        FoundGameData foundGameData =
                FoundGameData.getInstance(
                        0,
                        System.currentTimeMillis(),
                        1000,
                        choosingStageDuration,
                        "Some topic..",
                        userList);

        m_fragmentScenario.onFragment(new FragmentScenario.FragmentAction<PlayChoosingFragment>() {
            @Override
            public void perform(@NonNull final PlayChoosingFragment playChoosingFragment) {
                PlayFullViewModel playFullViewModel =
                        new ViewModelProvider(
                                playChoosingFragment.getActivity()).get(PlayFullViewModel.class);

                playFullViewModel.setFoundGameData(foundGameData);
            }
        });
    }

    @Test
    public void testAppearanceOnZeroUsersToChoose() {
        setUpPlayFullViewModel(0, 20000);

        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.
                onView(ViewMatchers.withId(R.id.play_choosing_user_list)).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(0)));
    }

    @Test
    public void testAppearanceOnThreeUsersToChoose() {
        setUpPlayFullViewModel(3, 20000);

        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.
                onView(ViewMatchers.withId(R.id.play_choosing_user_list)).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(3)));
    }

    @Test
    public void testChooseFirstUserOnThreeUsersToChoose() {
        setUpPlayFullViewModel(3, 20000);

        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.
                onView(ViewMatchers.withId(R.id.play_choosing_user_list)).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(3)));
        Espresso.
                onView(ViewMatchers.withId(R.id.play_choosing_user_list)).
                perform(UserCheckBoxClickViewAction.getInstance(0)).
                check(UserCheckBoxViewAssertion.getInstance(0, true)).
                check(UserCheckBoxViewAssertion.getInstance(1, false)).
                check(UserCheckBoxViewAssertion.getInstance(2, false));
    }

    @Test
    public void testChooseFirstAndThirdUsersOnThreeUsersToChoose() {
        setUpPlayFullViewModel(3, 20000);

        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.
                onView(ViewMatchers.withId(R.id.play_choosing_user_list)).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(3)));
        Espresso.
                onView(ViewMatchers.withId(R.id.play_choosing_user_list)).
                perform(UserCheckBoxClickViewAction.getInstance(0)).
                perform(UserCheckBoxClickViewAction.getInstance(2)).
                check(UserCheckBoxViewAssertion.getInstance(0, true)).
                check(UserCheckBoxViewAssertion.getInstance(1, false)).
                check(UserCheckBoxViewAssertion.getInstance(2, true));
    }

    private static PlayChoosingUserViewHolder getUserViewHolderByPosition(
            final View view,
            final int position)
    {
        RecyclerView recyclerView = (RecyclerView) view;
        PlayChoosingUserViewHolder viewHolder =
                (PlayChoosingUserViewHolder) recyclerView.
                        findViewHolderForLayoutPosition(position);

        if (viewHolder == null) throw new IllegalStateException();

        return viewHolder;
    }

    public static class UserCheckBoxClickViewAction implements ViewAction {
        private final int m_position;

        protected UserCheckBoxClickViewAction(final int position) {
            m_position = position;
        }

        public static ViewAction getInstance(final int position) {
            if (position < 0) return null;

            return new UserCheckBoxClickViewAction(position);
        }

        @Override
        public String getDescription() {
            return "Some description";
        }

        @Override
        public Matcher<View> getConstraints() {
            return ViewMatchers.withId(R.id.play_choosing_user_list);
        }

        @Override
        public void perform(
                final UiController uiController,
                final View view)
        {
            PlayChoosingUserViewHolder viewHolder =
                    getUserViewHolderByPosition(view, m_position);
            CheckBox checkBox =
                    viewHolder.itemView.findViewById(R.id.play_choosing_user_button_choose);

            ViewActions.click().perform(uiController, checkBox);
        }
    }

    public static class UserCheckBoxViewAssertion implements ViewAssertion {
        private final int m_position;
        private final boolean m_isChecked;

        protected UserCheckBoxViewAssertion(
                final int position,
                final boolean isChecked)
        {
            m_position = position;
            m_isChecked = isChecked;
        }

        public static ViewAssertion getInstance(
                final int position,
                final boolean isChecked)
        {
            if (position < 0) return null;

            return new UserCheckBoxViewAssertion(position, isChecked);
        }

        @Override
        public void check(
                final View view,
                final NoMatchingViewException noViewFoundException)
        {
            PlayChoosingUserViewHolder viewHolder =
                    getUserViewHolderByPosition(view, m_position);
            CheckBox checkBox =
                    viewHolder.itemView.findViewById(R.id.play_choosing_user_button_choose);

            if (checkBox.isChecked() != m_isChecked) throw noViewFoundException;
        }
    }
}
