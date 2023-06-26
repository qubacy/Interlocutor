package com.qubacy.interlocutor.data.profile.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.qubacy.interlocutor.data.profile.ProfileDataRepository;

public class ProfileDataStore implements ProfileDataRepository {
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
    public boolean setUsername(final String username) {
        if (username == null) return false;

        m_dataStore.edit().putString(C_USERNAME_PROP_NAME, username).apply();

        return true;
    }

    @Override
    public boolean setContact(final String contact) {
        if (contact == null) return false;

        m_dataStore.edit().putString(C_CONTACT_PROP_NAME, contact).apply();

        return true;
    }

//    final private RxDataStore<Preferences> m_dataStore;
//
//    private ProfileDataStore(final RxDataStore<Preferences> dataStore) {
//        m_dataStore = dataStore;
//    }
//
//    @Override
//    public String getUsername() {
//        Preferences.Key<String> usernameKey = PreferencesKeys.stringKey(C_USERNAME_PROP_NAME);
//        AtomicReference<String> username = new AtomicReference<>(null);
//
//        Single<Boolean> result =
//                m_dataStore.data().any(preferences -> {
//                    if (preferences.contains(usernameKey)) {
//                        username.set(preferences.get(usernameKey));
//                    }
//
//                    return false;
//                });
//
//        if (!result.blockingGet()) return null;
//
//        return username.get();
//    }
//
//    @Override
//    public String getContact() {
//        Preferences.Key<String> contactKey = PreferencesKeys.stringKey(C_CONTACT_PROP_NAME);
//        AtomicReference<String> contact = new AtomicReference<>(null);
//
//        Single<Boolean> result =
//                m_dataStore.data().any(preferences -> {
//                    if (preferences.contains(contactKey)) {
//                        contact.set(preferences.get(contactKey));
//                    }
//
//                    return false;
//                });
//
//        if (!result.blockingGet()) return null;
//
//        return contact.get();
//    }
//
//    @Override
//    public boolean setUsername(final String username) {
//        if (username == null) return false;
//
//        Preferences.Key<String> usernameKey = PreferencesKeys.stringKey(C_USERNAME_PROP_NAME);
//
//        Single<Preferences> result = m_dataStore.updateDataAsync(preferences -> {
//            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
//
//            mutablePreferences.set(usernameKey, username);
//
//            return Single.just(mutablePreferences);
//        });
//
//        if (result.)
//
//        return true;
//    }
//
//    @Override
//    public boolean setContact(final String contact) {
//
//    }



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
