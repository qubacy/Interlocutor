package com.qubacy.interlocutor.ui.screen.profile;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.profile.ProfileDataRepository;
import com.qubacy.interlocutor.data.profile.local.ProfileDataStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfileFragmentTest {
    private Context m_context = null;
    private ProfileDataRepository m_profileDataRepository = null;
    private FragmentScenario m_profileFragmentScenario = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        m_profileDataRepository =
                new ProfileDataStore.Builder().setContext(m_context).build();

        m_context.getSharedPreferences(
                ProfileDataStore.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE).
                edit().clear().commit();

        m_profileFragmentScenario = initFragmentScenario();
    }

    @Test
    public void testUsernameAndContactAreShownAsNullWhenDataStoreIsClear() {
        m_profileFragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.
                onView(ViewMatchers.withId(R.id.profile_username_input)).
                check(matches(ViewMatchers.withText("")));
        Espresso.
                onView(ViewMatchers.withId(R.id.profile_contact_input)).
                check(matches(ViewMatchers.withText("")));
    }

    @Test
    public void testUsernameAndContactAreShownAsInitializedWhenDataStoreIsInitialized() {
        String username = "someUsername";
        String contact = "someContact";

        assertTrue(
                m_profileDataRepository.setUsername(username) &&
                        m_profileDataRepository.setContact(contact));

        m_profileFragmentScenario = initFragmentScenario();

        m_profileFragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.
                onView(ViewMatchers.withId(R.id.profile_username_input)).
                check(matches(ViewMatchers.withText(username)));
        Espresso.
                onView(ViewMatchers.withId(R.id.profile_contact_input)).
                check(matches(ViewMatchers.withText(contact)));
    }

    @Test
    public void testUsernameAndContactAreNullAndConfirmClicked() {
        final View[] decorView = new View[1];

        m_profileFragmentScenario.onFragment(new FragmentScenario.FragmentAction() {
            @Override
            public void perform(@NonNull Fragment fragment) {
                decorView[0] = fragment.getActivity().getWindow().getDecorView();
            }
        });

        m_profileFragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.onView(ViewMatchers.withId(R.id.profile_confirm_button)).
                perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.profile_confirm_error_message_invalid_data)).
                inRoot(withDecorView(not(decorView[0]))).
                check(matches(isDisplayed()));
    }

    @Test
    public void testUsernameIsNullAndContactIsNotNullAndConfirmClicked()
            throws InterruptedException
    {
        final View[] decorView = new View[1];

        m_profileFragmentScenario.onFragment(new FragmentScenario.FragmentAction() {
            @Override
            public void perform(@NonNull Fragment fragment) {
                decorView[0] = fragment.getActivity().getWindow().getDecorView();
            }
        });

        m_profileFragmentScenario.moveToState(Lifecycle.State.STARTED);

        String contact = "someContact";

        Espresso.onView(ViewMatchers.withId(R.id.profile_contact_input)).
                perform(ViewActions.typeText(contact));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.profile_confirm_button)).
                perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.profile_confirm_error_message_invalid_data)).
                inRoot(withDecorView(not(decorView[0]))).
                check(matches(isDisplayed()));
    }

    @Test
    public void testUsernameIsNotNullAndContactIsNullAndConfirmClicked() {
        final View[] decorView = new View[1];

        m_profileFragmentScenario.onFragment(new FragmentScenario.FragmentAction() {
            @Override
            public void perform(@NonNull Fragment fragment) {
                decorView[0] = fragment.getActivity().getWindow().getDecorView();
            }
        });

        m_profileFragmentScenario.moveToState(Lifecycle.State.STARTED);

        String username = "someUsername";

        Espresso.onView(ViewMatchers.withId(R.id.profile_username_input)).
                perform(ViewActions.typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.profile_confirm_button)).
                perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.profile_confirm_error_message_invalid_data)).
                inRoot(withDecorView(not(decorView[0]))).
                check(matches(isDisplayed()));
    }

    @Test
    public void testUsernameAndContactAreNotNullAndConfirmClicked() {
        final View[] decorView = new View[1];

        m_profileFragmentScenario.onFragment(new FragmentScenario.FragmentAction() {
            @Override
            public void perform(@NonNull Fragment fragment) {
                decorView[0] = fragment.getActivity().getWindow().getDecorView();
            }
        });

        m_profileFragmentScenario.moveToState(Lifecycle.State.STARTED);

        String username = "someUsername";
        String contact = "someContact";

        Espresso.onView(ViewMatchers.withId(R.id.profile_username_input)).
                perform(ViewActions.typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.profile_contact_input)).
                perform(ViewActions.typeText(contact));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.profile_confirm_button)).
                perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.profile_confirm_error_message_saving_succeed)).
                inRoot(withDecorView(not(decorView[0]))).
                check(matches(isDisplayed()));
    }

    private FragmentScenario initFragmentScenario() {
        Bundle args = new Bundle();

        args.putSerializable(
                ProfileFragment.C_PROFILE_DATA_REPOSITORY_ARG_NAME, m_profileDataRepository);

        return FragmentScenario.launchInContainer(ProfileFragment.class, args);
    }
}
