package com.asesolutions.mobile.loteria;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class MainApplication extends Application {
    static Context context;

    public static Context getStaticContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (context == null) {
            context = this;
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
