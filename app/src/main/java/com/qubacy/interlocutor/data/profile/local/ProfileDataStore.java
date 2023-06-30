package com.qubacy.interlocutor.data.profile.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.struct.profile.local.Profile;
import com.qubacy.interlocutor.data.profile.ProfileDataSource;

public class ProfileDataStore implements ProfileDataSource {
    public static final String C_DATA_STORE_FILENAME = "profile";

    public static final String C_USERNAME_PROP_NAME = "username";
    public static final String C_CONTACT_PROP_NAME = "contact";

    final private SharedPreferences m_dataStore;

    private ProfileDataStore(final SharedPreferences dataStore) {
        m_dataStore = dataStore;
    }

    @Override
    public String getUsername() {
        return m_dataStore.getString(C_USERNAME_PROP_NAME, null);
    }

    @Override
    public String getContact() {
        return m_dataStore.getString(C_CONTACT_PROP_NAME, null);
    }

    @Override
    public Profile getProfile() {
        return Profile.getInstance(getUsername(), getContact());
    }

    @Override
    public boolean setUsername(@NonNull final String username) {
        m_dataStore.edit().putString(C_USERNAME_PROP_NAME, username).apply();

        return true;
    }

    @Override
    public boolean setContact(@NonNull final String contact) {
        m_dataStore.edit().putString(C_CONTACT_PROP_NAME, contact).apply();

        return true;
    }

    @Override
    public boolean setProfile(@NonNull final Profile profile) {
        return (setUsername(profile.getUsername()) && setContact(profile.getContact()));
    }

    public static class Builder {
        private Context m_context = null;
        private String m_filename = C_DATA_STORE_FILENAME;

        public Builder() {

        }

        public Builder setContext(final Context context) {
            m_context = context;

            return this;
        }

        public Builder setFilename(final String filename) {
            m_filename = filename;

            return this;
        }

        public ProfileDataStore build() {
            if (m_context == null || m_filename == null) return null;

            SharedPreferences sharedPreferences =
                    m_context.getSharedPreferences(C_DATA_STORE_FILENAME, Context.MODE_PRIVATE);

            if (sharedPreferences == null) return null;

            return new ProfileDataStore(sharedPreferences);

//            RxDataStore<Preferences> dataStore =
//                    new RxPreferenceDataStoreBuilder(m_context, m_filename).build();
//
//            if (dataStore == null) return null;
//            if (dataStore.isDisposed()) return null;
//
//            return new ProfileDataStore(dataStore);
        }
    }
}
