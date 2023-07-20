package com.qubacy.interlocutor.data.game.export.struct.results;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MatchedUserProfileData implements Parcelable {
    public static final String C_ID_PROP_NAME = "id";
    public static final String C_CONTACT_PROP_NAME = "contact";

    private final int m_id;
    private final String m_contact;

    protected MatchedUserProfileData(
            final int id,
            final String contact)
    {
        m_id = id;
        m_contact = contact;
    }

    protected MatchedUserProfileData(final Parcel in) {
        m_id = in.readInt();
        m_contact = in.readString();
    }

    public static final Creator<MatchedUserProfileData> CREATOR = new Creator<MatchedUserProfileData>() {
        @Override
        public MatchedUserProfileData createFromParcel(Parcel in) {
            return new MatchedUserProfileData(in);
        }

        @Override
        public MatchedUserProfileData[] newArray(int size) {
            return new MatchedUserProfileData[size];
        }
    };

    public static MatchedUserProfileData getInstance(
            final int id,
            final String contact)
    {
        if (id < 0 || contact == null) return null;

        return new MatchedUserProfileData(id, contact);
    }

    public int getId() {
        return m_id;
    }

    public String getContact() {
        return m_contact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(
            @NonNull final Parcel dest,
            int flags)
    {
        dest.writeInt(m_id);
        dest.writeString(m_contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchedUserProfileData that = (MatchedUserProfileData) o;

        return m_id == that.m_id &&
                Objects.equals(m_contact, that.m_contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_id, m_contact);
    }
}
