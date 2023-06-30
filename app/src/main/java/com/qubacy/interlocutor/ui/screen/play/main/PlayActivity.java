package com.qubacy.interlocutor.ui.screen.play.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.game.GameService;
import com.qubacy.interlocutor.data.general.struct.error.Error;
import com.qubacy.interlocutor.data.general.struct.error.utility.ErrorUtility;
import com.qubacy.interlocutor.data.general.struct.profile.local.Profile;
import com.qubacy.interlocutor.ui.main.broadcaster.MainActivityBroadcastReceiver;
import com.qubacy.interlocutor.ui.screen.play.main.error.PlayActivityErrorEnum;
import com.qubacy.interlocutor.ui.screen.play.main.model.PlayFullViewModel;

import java.io.Serializable;

public class PlayActivity extends AppCompatActivity
{
    public static final String C_PROFILE_ARG_NAME = "profile";

    private PlayFullViewModel m_viewModel = null;

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

        GameService.startDefault(this);
    }

    private boolean initWithArgs() {
        Intent intent = getIntent();

        if (intent == null) return false;

        Serializable profileSerializable = intent.getSerializableExtra(C_PROFILE_ARG_NAME);

        if (profileSerializable == null) return false;
        if (!(profileSerializable instanceof Profile)) return false;

        m_viewModel.setProfile((Profile) profileSerializable);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
