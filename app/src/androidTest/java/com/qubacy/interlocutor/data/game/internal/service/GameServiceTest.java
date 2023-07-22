package com.qubacy.interlocutor.data.game.internal.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ServiceTestRule;

import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;
import com.qubacy.interlocutor.data.game.internal.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.game.internal.processor.impl.GameSessionProcessorImpl;
import com.qubacy.interlocutor.data.game.internal.processor.impl.GameSessionProcessorImplFactory;
import com.qubacy.interlocutor.data.game.internal.service.launcher.GameServiceLauncherImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GameServiceTest {
    @Rule
    public final ServiceTestRule gameServiceRule = new ServiceTestRule();

    private Context m_context = null;
    private GameSessionProcessorFactory m_gameSessionProcessorFactory = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        m_gameSessionProcessorFactory =
                new GameSessionProcessorImplFactory();

    }

    @Test
    public void testServiceLaunchingManually() {
        Intent serviceIntent = new Intent(m_context, GameService.class);

        serviceIntent.putExtra(
                GameService.C_GAME_SESSION_PROCESSOR_FACTORY_PROP_NAME,
                m_gameSessionProcessorFactory);

        m_context.startService(serviceIntent);

        Assert.assertTrue(isGameServiceRunning());

        m_context.stopService(serviceIntent);

        Assert.assertFalse(isGameServiceRunning());
    }

    @Test
    public void testServiceLaunchingUsingStaticStartMethod() {
        GameService.start(m_context, m_gameSessionProcessorFactory);

        Assert.assertTrue(isGameServiceRunning());

        GameService.stop(m_context);

        Assert.assertFalse(isGameServiceRunning());
    }

    @Test
    public void testServiceLaunchingUsingLauncher() {
        GameServiceLauncher gameServiceLauncher =
                GameServiceLauncherImpl.getInstance(m_gameSessionProcessorFactory);

        gameServiceLauncher.startService(m_context);

        Assert.assertTrue(isGameServiceRunning());

        gameServiceLauncher.stopService(m_context);

        Assert.assertFalse(isGameServiceRunning());
    }

    private boolean isGameServiceRunning() {
        ActivityManager activityManager =
                (ActivityManager) m_context.getSystemService(Context.ACTIVITY_SERVICE);

        for (final ActivityManager.RunningServiceInfo runningServiceInfo :
                activityManager.getRunningServices(Integer.MAX_VALUE))
        {
            if (runningServiceInfo.service.getClassName().equals(GameService.class.getName())) {
                return true;
            }
        }

        return false;
    }
}
