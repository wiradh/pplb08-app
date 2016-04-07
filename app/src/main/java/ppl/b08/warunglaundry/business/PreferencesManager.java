package ppl.b08.warunglaundry.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import ppl.b08.warunglaundry.BuildConfig;

/**
 * Created by Andi Fajar on 07/04/2016.
 * Best practice untuk membuat instance dari preferencesManager
 */
public class PreferencesManager {

    private static final String PREF_NAME="pref.name";
    private static final String KEY_PREFERENCES_VERSION="pref.version";
    private static final String KEY_ID="pref.id";
    private static final String KEY_NAME="pref.name";
    private static final String KEY_EMAIL="pref.email";



    private static PreferencesManager instance;
    private final SharedPreferences pref;

    private PreferencesManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        checkPreferences(pref);
    }

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {

        }
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context);
//            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
//                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    private static void checkPreferences(SharedPreferences thePreferences) {
        final double oldVersion = thePreferences.getInt(KEY_PREFERENCES_VERSION, 1);
        int currentVersion = BuildConfig.VERSION_CODE;
        if (oldVersion < currentVersion) {
            final SharedPreferences.Editor edit = thePreferences.edit();
            edit.clear();
            edit.putInt(KEY_PREFERENCES_VERSION, currentVersion);
            edit.apply();
        }
    }

    public void setIdValue(String value) {
        pref.edit()
                .putString(KEY_ID, value)
                .apply();
    }

    public long getIdValue() {
        return pref.getLong(KEY_ID, -1);
    }

    public void remove(String key) {
        pref.edit()
                .remove(key)
                .apply();
    }

    public boolean clear() {
        return pref.edit()
                .clear()
                .commit();
    }


}
