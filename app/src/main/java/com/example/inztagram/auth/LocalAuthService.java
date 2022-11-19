package com.example.inztagram.auth;

public class LocalAuthService {
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
    }

    public String getSecretKey() {
        return "";
    }

    public Boolean isUesrLoggedIn() {
        return true;
    }

    public void logoutUser() {

    }
}
