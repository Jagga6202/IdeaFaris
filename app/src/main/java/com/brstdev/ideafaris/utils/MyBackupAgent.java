package com.brstdev.ideafaris.utils;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.util.Log;

/**
 * Created by brst-pc16 on 9/27/16.
 */
public class MyBackupAgent extends BackupAgentHelper {
    // The names of the SharedPreferences groups that the application maintains.  These
    // are the same strings that are passed to getSharedPreferences(String, int).
    static final String PREFS_TEST = "testprefs";

    // An arbitrary string used within the BackupAgentHelper implementation to
    // identify the SharedPreferenceBackupHelper's data.
    static final String MY_PREFS_BACKUP_KEY = "myprefs";

    // Simply allocate a helper and install it
    @Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS_TEST);
        addHelper(MY_PREFS_BACKUP_KEY, helper);
        Log.d("Test", "Adding backupagent...");
    }
}
