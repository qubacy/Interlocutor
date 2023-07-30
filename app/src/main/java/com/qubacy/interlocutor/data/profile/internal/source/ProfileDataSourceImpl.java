package com.qubacy.interlocutor.data.profile.internal.source;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.qubacy.interlocutor.data.general.export.struct.profile.LanguageEnum;
import com.qubacy.interlocutor.data.general.export.struct.profile.Profile;
import com.qubacy.interlocutor.data.profile.export.source.ProfileDataSource;

public class ProfileDataSourceImpl implements ProfileDataSource {
    public static final String C_DATA_STORE_FILENAME = "profile";

    public static final String C_USERNAME_PROP_NAME = "username";
    public static final String C_CONTACT_PROP_NAME = "contact";
    public static final String C_LANGUAGE_PROP_NAME = "lang";

    final private SharedPreferences m_dataStore;

    private ProfileDataSourceImpl(final SharedPreferences dataStore) {
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
    public LanguageEnum getLanguage() {
        int langId = m_dataStore.getInt(C_LANGUAGE_PROP_NAME, 0);

        return LanguageEnum.getLanguageById(langId);
    }

    @Override
    public Profile getProfile() {
        return Profile.getInstance(getUsername(), getContact(), getLanguage());
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
    public boolean setLanguage(@NonNull final LanguageEnum language) {
        m_dataStore.edit().putInt(C_LANGUAGE_PROP_NAME, language.getId()).apply();

        return true;
    }

    @Override
    public boolean setProfile(@NonNull final Profile profile) {
        return (setUsername(profile.getUsername()) &&
                setContact(profile.getContact()) &&
                setLanguage(profile.getLang()));
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

        public ProfileDataSourceImpl build() {
            if (m_context == null || m_filename == null) return null;

            SharedPreferences sharedPreferences =
                    m_context.getSharedPreferences(C_DATA_STORE_FILENAME, Context.MODE_PRIVATE);

            if (sharedPreferences == null) return null;

            return new ProfileDataSourceImpl(sharedPreferences);
        }
    }
}
