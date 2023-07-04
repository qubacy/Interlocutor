package com.qubacy.interlocutor.data.game.internal.processor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.struct.searching.FoundGameData;
import com.qubacy.interlocutor.data.game.internal.struct.message.RemoteMessage;
import com.qubacy.interlocutor.data.game.internal.struct.searching.RemoteFoundGameData;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.general.internal.struct.profile.RemoteProfile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class GameSessionProcessorImplTest {
    private Context m_context = null;
    private GameSessionProcessorCallback m_callback = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        m_callback = new GameSessionProcessorCallback() {
            @Override
            public void gameFound(@NonNull final RemoteFoundGameData foundGameData) {
                return;
            }

            @Override
            public void gameSearchingAborted() {
                return;
            }

            @Override
            public void errorOccurred(@NonNull final Error error) {
                return;
            }

            @Override
            public void messageSent() {
                return;
            }

            @Override
            public void messageReceived(@NonNull final RemoteMessage message) {
                return;
            }

            @Override
            public void usersMadeChoice(@NonNull final List<RemoteProfile> matchedUserList) {
                return;
            }
        };
    }

    @Test
    public void testLaunchingStopping() {
        GameSessionProcessor gameSessionProcessor = GameSessionProcessorImpl.getInstance();

        gameSessionProcessor.launch(m_callback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        gameSessionProcessor.stop();

        Assert.assertFalse(gameSessionProcessor.isRunning());
    }

    @Test
    public void testSearching() {
        GameSessionProcessor gameSessionProcessor = GameSessionProcessorImpl.getInstance();

        gameSessionProcessor.launch(m_callback, m_context);

        Assert.assertTrue(gameSessionProcessor.isRunning());

        Profile profile = Profile.getInstance("username", "contact");

        Assert.assertTrue(gameSessionProcessor.startSearching(profile));
        Assert.assertTrue(gameSessionProcessor.stopSearching());

        gameSessionProcessor.stop();

        Assert.assertFalse(gameSessionProcessor.isRunning());
    }
}
