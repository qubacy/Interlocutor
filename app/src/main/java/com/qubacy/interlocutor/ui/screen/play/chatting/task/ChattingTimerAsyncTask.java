package com.qubacy.interlocutor.ui.screen.play.chatting.task;

import android.os.AsyncTask;
import android.os.Process;
import android.os.SystemClock;
import android.widget.TextView;

import com.qubacy.interlocutor.data.utility.time.TimeUtility;

public class ChattingTimerAsyncTask extends AsyncTask<Void, Long, Void> {
    private static final long C_TIMER_UPDATE_TIMEOUT = 1000;

    private final ChattingTimerAsyncTaskCallback m_callback;
    private final TextView m_timerTextView;

    protected ChattingTimerAsyncTask(
            final ChattingTimerAsyncTaskCallback callback,
            final TextView timerTextView)
    {
        m_callback = callback;
        m_timerTextView = timerTextView;
    }

    public static ChattingTimerAsyncTask getInstance(
            final ChattingTimerAsyncTaskCallback callback,
            final TextView timerTextView)
    {
        if (callback == null || timerTextView == null)
            return null;

        return new ChattingTimerAsyncTask(callback, timerTextView);
    }

    @Override
    protected Void doInBackground(final Void... voids) {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (!isCancelled()) {
            SystemClock.sleep(C_TIMER_UPDATE_TIMEOUT);

            long curRemainingTime = m_callback.getChattingTimeRemaining();

            if (curRemainingTime <= 0) break;

            long newRemainingTime = curRemainingTime - C_TIMER_UPDATE_TIMEOUT;

            m_callback.setChattingTimeRemaining(newRemainingTime);

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
