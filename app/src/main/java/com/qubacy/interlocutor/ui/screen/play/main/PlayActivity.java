package com.qubacy.interlocutor.ui.screen.play.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessor;
import com.qubacy.interlocutor.data.game.export.processor.GameSessionProcessorFactory;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.main.error.PlayActivityErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;

import java.io.Serializable;

public class PlayActivity extends AppCompatActivity
{
    public static final String C_GAME_SERVICE_LAUNCHER_ARG_NAME = "gameServiceLauncher";
    public static final String C_PROFILE_ARG_NAME = "profile";

    private PlayFullViewModel m_viewModel = null;
    private GameServiceLauncher m_gameServiceLauncher = null;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        m_viewModel = new ViewModelProvider(this).get(PlayFullViewModel.class);

        if (!initWithArgs()) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    this,
                    PlayActivityErrorEnum.NULL_ARGUMENTS.getResourceCode(),
                    PlayActivityErrorEnum.NULL_ARGUMENTS.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }

        if (!startServices()) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            this,
                            PlayActivityErrorEnum.SERVICES_START_FAILED.getResourceCode(),
                            PlayActivityErrorEnum.SERVICES_START_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }
    }

    private boolean startServices() {
        m_gameServiceLauncher.startService(this);

        return true;
    }

    private boolean initWithArgs() {
        Intent intent = getIntent();

        if (intent == null) return false;

        Serializable profileSerializable =
                intent.getSerializableExtra(C_PROFILE_ARG_NAME);
        Serializable gameServiceLauncherSerializable =
                intent.getSerializableExtra(C_GAME_SERVICE_LAUNCHER_ARG_NAME);

        if (profileSerializable == null || gameServiceLauncherSerializable == null)
            return false;
        if (!(profileSerializable instanceof Profile) ||
            !(gameServiceLauncherSerializable instanceof GameServiceLauncher))
        {
            return false;
        }

        m_viewModel.setProfile((Profile) profileSerializable);

        m_gameServiceLauncher = (GameServiceLauncher) gameServiceLauncherSerializable;

        return true;
    }

    @Override
    public void onBackPressed() {
        m_gameServiceLauncher.stopService(this);

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
