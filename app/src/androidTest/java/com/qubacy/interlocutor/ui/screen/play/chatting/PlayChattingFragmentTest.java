package com.qubacy.interlocutor.ui.screen.play.chatting;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;

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
    public void testPlayChattingFragmentAppearance() {
        m_fragmentScenario.moveToState(Lifecycle.State.STARTED);


    }

}
