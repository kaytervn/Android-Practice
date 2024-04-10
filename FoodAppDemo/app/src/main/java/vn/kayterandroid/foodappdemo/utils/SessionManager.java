package vn.kayterandroid.foodappdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "LoginPreference";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SessionManager instance;

    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveLoginDetails(String email, String password) {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public void clearLoginDetails() {
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }
}