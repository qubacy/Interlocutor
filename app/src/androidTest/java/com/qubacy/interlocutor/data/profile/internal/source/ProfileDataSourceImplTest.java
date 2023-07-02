package com.qubacy.interlocutor.data.profile.internal.source;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.qubacy.interlocutor.data.profile.internal.source.ProfileDataSourceImpl;

@RunWith(AndroidJUnit4.class)
public class ProfileDataSourceImplTest {
    private Context m_context = null;

    @Before
    public void setUp() {
        m_context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testCreatingProfileDataStoreWithNullContext() {
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(null).build();

        assertNull(profileDataStore);
    }

    @Test
    public void testCreatingProfileDataStoreWithNullFilename() {
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(null).build();

        assertNull(profileDataStore);
    }

    @Test
    public void testCreatingProfileDataStoreWithCustomFilename() {
        String filename = "some_file";
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).setFilename(filename).build();

        assertNotNull(profileDataStore);

        m_context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit().clear().commit();
    }

    @Test
    public void testReadingNullUsernameFromSharedPreferences() {
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();
        String username = profileDataStore.getUsername();

        assertNull(username);
    }

    @Test
    public void testReadingNotNullUsernameFromSharedPreferences() {
        String someUsername = "someUsername";

        m_context.getSharedPreferences(
                ProfileDataSourceImpl.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE).edit().
                putString(ProfileDataSourceImpl.C_USERNAME_PROP_NAME, someUsername).
                commit();

        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();
        String username = profileDataStore.getUsername();

        assertEquals(username, someUsername);
    }

    @Test
    public void testReadingNullContactFromSharedPreferences() {
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();
        String contact = profileDataStore.getContact();

        assertNull(contact);
    }

    @Test
    public void testReadingNotNullContactFromSharedPreferences() {
        String someContact = "someContact";

        m_context.getSharedPreferences(
                        ProfileDataSourceImpl.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE).edit().
                putString(ProfileDataSourceImpl.C_CONTACT_PROP_NAME, someContact).
                commit();

        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();
        String contact = profileDataStore.getContact();

        assertEquals(contact, someContact);
    }

    @Test
    public void testSettingNullUsernameToSharedPreferences() {
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();

        assertFalse(profileDataStore.setUsername(null));
    }

    @Test
    public void testSettingNotNullUsernameToSharedPreferences() {
        String username = "someUsername";

        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();

        profileDataStore.setUsername(username);

        String storedUsername = profileDataStore.getUsername();

        assertEquals(username, storedUsername);
    }

    @Test
    public void testSettingNullContactToSharedPreferences() {
        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();

        assertFalse(profileDataStore.setContact(null));
    }

    @Test
    public void testSettingNotNullContactToSharedPreferences() {
        String contact = "someContact";

        ProfileDataSourceImpl profileDataStore =
                new ProfileDataSourceImpl.Builder().setContext(m_context).build();

        profileDataStore.setContact(contact);

        String storedContact = profileDataStore.getContact();

        assertEquals(contact, storedContact);
    }

    @After
    public void cleanUp() {
        SharedPreferences sharedPreferences =
                m_context.getSharedPreferences(
                    ProfileDataSourceImpl.C_DATA_STORE_FILENAME, Context.MODE_PRIVATE);

        sharedPreferences.edit().clear().commit();
    }
}
