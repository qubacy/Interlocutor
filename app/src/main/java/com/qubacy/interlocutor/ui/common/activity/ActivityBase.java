package com.qubacy.interlocutor.ui.common.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class ActivityBase extends AppCompatActivity {
    protected boolean setCustomActionBar(final int actionBarResId) {
        Toolbar toolbar = findViewById(actionBarResId);

        if (toolbar == null) return false;

        setSupportActionBar(toolbar);

        return true;
    }
}
