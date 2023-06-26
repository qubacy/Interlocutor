package com.qubacy.interlocutor.data.profile;

import java.io.Serializable;

public interface ProfileDataRepository extends Serializable {
    public String getUsername();
    public String getContact();
    public boolean setUsername(final String username);
    public boolean setContact(final String contact);
}
