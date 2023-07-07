package com.qubacy.interlocutor.ui.screen.play.common.task;

import android.os.AsyncTask;
import android.os.Process;
import android.os.SystemClock;
import android.widget.TextView;

import com.qubacy.interlocutor.data.general.export.utility.time.TimeUtility;

public class TextViewTimerAsyncTask extends AsyncTask<Void, Long, Void> {
    private static final long C_DEFAULT_TIMER_UPDATE_TIMEOUT = 1000;

    private final TextViewTimerAsyncTaskCallback m_callback;
    private final TextView m_timerTextView;
    private final long m_updateTime;

    protected TextViewTimerAsyncTask(
            final TextViewTimerAsyncTaskCallback callback,
            final TextView timerTextView,
            final long updateTime)
    {
        m_callback = callback;
        m_timerTextView = timerTextView;
        m_updateTime = updateTime;
    }

    public static TextViewTimerAsyncTask getInstance(
            final TextViewTimerAsyncTaskCallback callback,
            final TextView timerTextView)
    {
        if (callback == null || timerTextView == null)
            return null;

        return new TextViewTimerAsyncTask(
                callback, timerTextView, C_DEFAULT_TIMER_UPDATE_TIMEOUT);
    }

    public static TextViewTimerAsyncTask getInstance(
            final TextViewTimerAsyncTaskCallback callback,
            final TextView timerTextView,
            final long updateTime)
    {
        if (callback == null || timerTextView == null || updateTime <= 0)
            return null;

        return new TextViewTimerAsyncTask(
                callback, timerTextView, updateTime);
    }

    @Override
    protected Void doInBackground(final Void... voids) {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (!isCancelled()) {
            SystemClock.sleep(m_updateTime);

            long curRemainingTime = m_callback.getRemainingTime();

            if (curRemainingTime <= 0) break;

            long newRemainingTime = curRemainingTime - m_updateTime;

            m_callback.setRemainingTime(newRemainingTime);

            publishProgress(newRemainingTime);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(final Long... values) {
        Long lastUpdateValue = values[values.length - 1];
        String newTime = TimeUtility.millisecondsToMinutesString(lastUpdateValue);

        m_timerTextView.setText(newTime);
    }
}
