package com.qubacy.interlocutor.data.general.struct.error.utility;

import android.content.Context;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.game.broadcast.error.GameServiceBroadcastReceiverErrorEnum;
import com.qubacy.interlocutor.data.general.struct.error.Error;

public class ErrorUtility {
    public static Error getErrorByStringResourceCodeAndFlag(
            @NonNull final Context context,
            final int resourceCode,
            final boolean isCritical)
    {
        String errorText =
                context.getString(
                        GameServiceBroadcastReceiverErrorEnum.
                                INCORRECT_COMMAND.
                                getResourceCode());

        return Error.getInstance(errorText, isCritical);
    }
}
