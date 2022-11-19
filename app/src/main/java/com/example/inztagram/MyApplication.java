package com.example.inztagram;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = this.getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
