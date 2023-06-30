package com.qubacy.interlocutor.data.game.struct;

import android.util.Pair;

import com.qubacy.interlocutor.data.general.struct.profile.other.OtherProfilePublic;

import java.io.Serializable;
import java.util.List;

public class FoundGameData implements Serializable {
    private List<Pair<Integer, OtherProfilePublic>> m_idProfilePairList;
}
