package com.qubacy.interlocutor.ui.screen.play.chatting;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PlayChattingFragmentTest {
    private Context m_context = null;
    private FragmentScenario m_fragmentScenario = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        m_fragmentScenario = FragmentScenario.launchInContainer(PlayChattingFragment.class);
    }

    @Test
    public void testTypeNewMessage() {
        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        String testMessageText = "hi there";

        Espresso.
                onView(ViewMatchers.withId(R.id.play_chatting_section_sending_message_text)).
                perform(ViewActions.typeText(testMessageText)).
                check(ViewAssertions.matches(ViewMatchers.withText(testMessageText)));
    }

    @Test
    public void testSendingNewMessage() {
        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);

        String testMessageText = "hi there";

        Espresso.
                onView(ViewMatchers.withId(R.id.play_chatting_section_sending_message_text)).
                perform(ViewActions.typeText(testMessageText));
        Espresso.
                onView(ViewMatchers.withId(R.id.play_chatting_section_sending_button_send)).
                perform(ViewActions.click());
        Espresso.
                onView(ViewMatchers.withId(R.id.play_chatting_section_sending_message_text)).
                check(ViewAssertions.matches(ViewMatchers.withText("")));

    }

}
