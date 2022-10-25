package com.arhamsoft.deskilz;

import android.app.Application;
import android.content.Context;

import com.arhamsoft.deskilz.utils.StaticFields;
import com.google.firebase.FirebaseApp;

public class AppController extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static void initSDK(String key) {
        StaticFields.INSTANCE.setKey(key);
    }

    public static Context getContext() {
        return context;
    }
}
