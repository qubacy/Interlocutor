package com.qubacy.interlocutor.data.profile.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProfileDataStoreTest {
    private Context m_context = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testCreatingProfileDataStoreWithNullContext() {
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(null).build();

        assertNull(profileDataStore);
    }

    @Test
    public void testCreatingProfileDataStoreWithNullFilename() {
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(null).build();

        assertNull(profileDataStore);
    }

    @Test
    public void testCreatingProfileDataStoreWithCustomFilename() {
        String filename = "some_file";
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).setFilename(filename).build();

        assertNotNull(profileDataStore);

        m_context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit().clear().commit();
    }

    @Test
    public void testReadingNullUsernameFromSharedPreferences() {
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();
        String username = profileDataStore.getUsername();

        assertNull(username);
    }

    @Test
    public void testReadingNotNullUsernameFromSharedPreferences() {
        String someUsername = "someUsername";

        m_context.getSharedPreferences(
                ProfileDataStore.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE).edit().
                putString(ProfileDataStore.C_USERNAME_PROP_NAME, someUsername).
                commit();

        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();
        String username = profileDataStore.getUsername();

        assertEquals(username, someUsername);
    }

    @Test
    public void testReadingNullContactFromSharedPreferences() {
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();
        String contact = profileDataStore.getContact();

        assertNull(contact);
    }

    @Test
    public void testReadingNotNullContactFromSharedPreferences() {
        String someContact = "someContact";

        m_context.getSharedPreferences(
                        ProfileDataStore.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE).edit().
                putString(ProfileDataStore.C_CONTACT_PROP_NAME, someContact).
                commit();

        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();
        String contact = profileDataStore.getContact();

        assertEquals(contact, someContact);
    }

    @Test
    public void testSettingNullUsernameToSharedPreferences() {
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();

        assertFalse(profileDataStore.setUsername(null));
    }

    @Test
    public void testSettingNotNullUsernameToSharedPreferences() {
        String username = "someUsername";

        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();

        profileDataStore.setUsername(username);

        String storedUsername = profileDataStore.getUsername();

        assertEquals(username, storedUsername);
    }

    @Test
    public void testSettingNullContactToSharedPreferences() {
        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();

        assertFalse(profileDataStore.setContact(null));
    }

    @Test
    public void testSettingNotNullContactToSharedPreferences() {
        String contact = "someContact";

        ProfileDataStore profileDataStore =
                new ProfileDataStore.Builder().setContext(m_context).build();

        profileDataStore.setContact(contact);

        String storedContact = profileDataStore.getContact();

        assertEquals(contact, storedContact);
    }

    @After
    public void cleanUp() {
        SharedPreferences sharedPreferences =
                m_context.getSharedPreferences(
                    ProfileDataStore.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE);

        sharedPreferences.edit().clear().commit();
    }
}
