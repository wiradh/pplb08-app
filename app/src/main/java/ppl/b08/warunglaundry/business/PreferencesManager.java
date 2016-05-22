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

    private static final String PREF_NAME = "NAME";
    private static final String KEY_PREFERENCES_VERSION = "PREFERENCES_VERSION";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_ROLE = "ROLE";
    private static final String KEY_DETIL_ALAMAT = "DETIL_ALAMAT";
    private static final String KEY_TOKEN = "TOKEN";



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

    public void setIdValue(long value) {
        pref.edit()
                .putLong(KEY_ID, value)
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

    public String getRoleValue() {
        return pref.getString(KEY_ROLE, "cu");
    }

    public void setRoleValue(String role) {
        pref.edit().putString(KEY_ROLE, role).apply();
    }

    public String getAlamatValue() {
        return pref.getString(KEY_DETIL_ALAMAT, "");
    }

    public void setAlamatValue(String alamat) {
        pref.edit().putString(KEY_DETIL_ALAMAT, alamat).apply();
    }

    public boolean clear() {
        return pref.edit()
                .clear()
                .commit();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public void setToken(String tokon) {
        pref.edit().putString(KEY_TOKEN, tokon).apply();
    }

    public String getName() {
        return pref.getString(KEY_NAME, "");
    }

    public void setName(String tokon) {
        pref.edit().putString(KEY_NAME, tokon).apply();
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setEmail(String tokon) {
        pref.edit().putString(KEY_EMAIL, tokon).apply();
    }
}
