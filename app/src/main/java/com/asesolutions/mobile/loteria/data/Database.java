package com.asesolutions.mobile.loteria.data;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.asesolutions.mobile.loteria.MainApplication;

import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String NAME = "database.db";
    private static final int VERSION = 1;
    private static Database instance = null;

    protected Database() {
        super(MainApplication.getStaticContext(), NAME, null, VERSION);
    }

    private static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public static void save(MegaMillionsApiResult result) {
        if (exists(result)) {
            return;
        }

        SQLiteDatabase writableDatabase = getInstance().getWritableDatabase();

        writableDatabase.insert(
                MegaMillionsContract.TABLE_NAME,
                null,
                MegaMillionsContract.getContentValues(result));
    }

    public static void save(List<MegaMillionsApiResult> megaMillionsApiResults) {
        SQLiteDatabase writableDatabase = getInstance().getWritableDatabase();

        writableDatabase.beginTransaction();

        for (MegaMillionsApiResult result : megaMillionsApiResults) {
            if (exists(result)) {
                continue;
            }

            long d = writableDatabase.insert(
                    MegaMillionsContract.TABLE_NAME,
                    null,
                    MegaMillionsContract.getContentValues(result));
        }

        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
    }

    public static boolean exists(MegaMillionsApiResult megaMillionsApiResult) {
        return DatabaseUtils.queryNumEntries(
                getInstance().getReadableDatabase(),
                MegaMillionsContract.TABLE_NAME,
                MegaMillionsContract.Column.DRAW_DATE + "=?",
                new String[]{megaMillionsApiResult.getDrawDate()}) > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MegaMillionsContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MegaMillionsContract.SQL_DELETE_TABLE);
    }
}
