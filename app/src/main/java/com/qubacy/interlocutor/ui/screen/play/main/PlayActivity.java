package com.qubacy.interlocutor.ui.screen.play.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.export.service.launcher.GameServiceLauncher;
import com.qubacy.interlocutor.data.general.export.struct.error.Error;
import com.qubacy.interlocutor.data.general.export.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.main.error.PlayActivityErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayActivityViewModel;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;

import java.io.Serializable;

public class PlayActivity extends AppCompatActivity
{
    public static final String C_GAME_SERVICE_LAUNCHER_ARG_NAME = "gameServiceLauncher";
    public static final String C_PROFILE_ARG_NAME = "profile";

    private PlayFullViewModel m_playFullViewModel = null;
    private PlayActivityViewModel m_playActivityViewModel = null;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        m_playActivityViewModel =
                new ViewModelProvider(this).get(PlayActivityViewModel.class);
        m_playFullViewModel =
                new ViewModelProvider(this).get(PlayFullViewModel.class);

        if (!initWithArgs()) {
            Error error =
                ErrorUtility.getErrorByStringResourceCodeAndFlag(
                    this,
                    PlayActivityErrorEnum.NULL_ARGUMENTS.getResourceCode(),
                    PlayActivityErrorEnum.NULL_ARGUMENTS.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }

        if (!m_playActivityViewModel.startServices(this)) {
            Error error =
                    ErrorUtility.getErrorByStringResourceCodeAndFlag(
                            this,
                            PlayActivityErrorEnum.SERVICES_START_FAILED.getResourceCode(),
                            PlayActivityErrorEnum.SERVICES_START_FAILED.isCritical());

            MainActivityBroadcastReceiver.broadcastError(this, error);

            return;
        }
    }

    private boolean initWithArgs() {
        if (m_playActivityViewModel.isInitialized())
            return true;

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

        m_playFullViewModel.setProfile((Profile) profileSerializable);

        m_playActivityViewModel.setGameServiceLauncher(
                (GameServiceLauncher) gameServiceLauncherSerializable);

        return true;
    }

    @Override
    public void onBackPressed() {
        m_playActivityViewModel.getGameServiceLauncher().stopService(this);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
