package com.asesolutions.mobile.loteria.history;

import android.content.SharedPreferences;

import com.asesolutions.mobile.loteria.MainApplication;

import java.util.concurrent.TimeUnit;

public class LottoResultsPreferences {
    private static final String CLASS_NAME = LottoResultsPreferences.class.getCanonicalName();
    private static final String PREF_KEY_LAST_UPDATE = CLASS_NAME + "LAST_UPDATE";
    private static final long UPDATE_PERIOD_IN_HOURS = 24;

    public static boolean shouldRefresh() {
        SharedPreferences preferences = MainApplication.getSharedPreferences();

        // Get the last update and current time
        long lastUpdate = preferences.getLong(PREF_KEY_LAST_UPDATE, 0);
        long currentTime = System.currentTimeMillis();

        // Compute how many hours have passed
        long elapsedHours = TimeUnit.MILLISECONDS.toHours(currentTime - lastUpdate);

        // Save this last update time
        saveLastUpdateTime(lastUpdate);

        // Check if we should update given the period
        return elapsedHours >= UPDATE_PERIOD_IN_HOURS;
    }

    private static void saveLastUpdateTime(long time) {
        MainApplication.getSharedPreferences().edit().putLong(PREF_KEY_LAST_UPDATE, time).commit();
    }
}
