package com.qubacy.interlocutor.data.general.internal.struct;

public interface DataMapper<I, O> {
    public O map(final I input);
}
