package com.asesolutions.mobile.loteria.database;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.asesolutions.mobile.loteria.DateUtil;
import com.asesolutions.mobile.loteria.MainApplication;
import com.asesolutions.mobile.loteria.data.MegaMillionsApiResult;

import java.text.ParseException;
import java.util.List;

import rx.Observable;
import timber.log.Timber;

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

        attemptInsert(getInstance().getWritableDatabase(), result);
    }

    public static Observable<Void> save(List<MegaMillionsApiResult> megaMillionsApiResults) {
        SQLiteDatabase writableDatabase = getInstance().getWritableDatabase();

        writableDatabase.beginTransaction();

        for (MegaMillionsApiResult result : megaMillionsApiResults) {
            if (exists(result)) {
                continue;
            }

            attemptInsert(writableDatabase, result);
        }

        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();

        return Observable.empty();
    }

    private static void attemptInsert(SQLiteDatabase writableDatabase, MegaMillionsApiResult result) {
        try {
            writableDatabase.insert(
                    MegaMillionsContract.TABLE_NAME,
                    null,
                    MegaMillionsContract.getContentValues(result));
        } catch (ParseException e) {
            Timber.e(e, "Crap, the API dateTime format is not in the right format");
        }
    }

    public static boolean exists(MegaMillionsApiResult megaMillionsApiResult) {
        try {
            long unixTime = DateUtil.iso8601toUnixTime(megaMillionsApiResult.getDrawDate());

            return DatabaseUtils.queryNumEntries(
                    getInstance().getReadableDatabase(),
                    MegaMillionsContract.TABLE_NAME,
                    MegaMillionsContract.Column.DRAW_DATE + "=?",
                    new String[]{String.valueOf(unixTime)}) > 0;
        } catch (ParseException e) {
            Timber.e(e, "Crap, the API dateTime format is not in the right format");
        }

        return false;
    }

    public static Observable<Cursor> getMegaMillionsCursor() {

        return Observable.fromCallable(() ->
                getInstance().getReadableDatabase().query(
                        MegaMillionsContract.TABLE_NAME,
                        MegaMillionsContract.PROJECTION,
                        null, null, null, null,
                        MegaMillionsContract.Column.DRAW_DATE + " DESC"));
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
