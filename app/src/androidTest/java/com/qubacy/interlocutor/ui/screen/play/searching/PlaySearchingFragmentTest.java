package com.qubacy.interlocutor.ui.screen.play.searching;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PlaySearchingFragmentTest {
    private Context m_context = null;
    private FragmentScenario m_fragmentScenario = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        m_fragmentScenario = FragmentScenario.launchInContainer(PlaySearchingFragment.class);
    }

    @Test
    public void testGameSearchingAboutButtonClicked() {
        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        Espresso.onView(ViewMatchers.
                withId(R.id.play_searching_button_abort)).
                perform(ViewActions.click());

        // what actually to do? how to check?
    }

}
