package com.example.inztagram.Service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inztagram.MyApplication;

public class LocalAuthService {
    private final String SHARED_PREFS = "SHARED_PREFERENCES";
    private final String USER_UUID = "SHARED_PREFERENCES_USER_UUID";
    private LocalAuthService() {
    }
    private static LocalAuthService localAuthService;

    public static LocalAuthService getInstance() {
        if(localAuthService == null) {
            localAuthService = new LocalAuthService();
        }
        return localAuthService;
    }

    public void saveSecretKey(String uuid) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_UUID, uuid);
        editor.apply();
    }

    public String getSecretKey() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String uuidString = sharedPreferences.getString(USER_UUID, null);
        return uuidString;
    }

    public Boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String uuidString = sharedPreferences.getString(USER_UUID, null);
        return (uuidString != null);
    }

    public void logoutUser() {
        SharedPreferences settings = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}
