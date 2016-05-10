package com.asesolutions.mobile.loteria.data;

import android.content.ContentValues;
import android.provider.BaseColumns;

public class MegaMillionsContract {
    public static final String TABLE_NAME = "mega_millions";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    Column._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Column.DRAW_DATE + " TEXT NOT NULL," +
                    Column.MEGA_BALL + " INTEGER NOT NULL," +
                    Column.WINNING_NUMBERS + " TEXT NOT NULL)";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String[] PROJECTION = {
            Column._ID,
            Column.DRAW_DATE,
            Column.MEGA_BALL,
            Column.WINNING_NUMBERS
    };

    public static ContentValues getContentValues(MegaMillionsApiResult result) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Column.DRAW_DATE, result.getDrawDate());
        contentValues.put(Column.MEGA_BALL, result.getMegaBall());
        contentValues.put(Column.WINNING_NUMBERS, result.getWinningNumbers());

        return contentValues;
    }

    public static abstract class Column implements BaseColumns {
        public static final String DRAW_DATE = "draw_date";
        public static final String MEGA_BALL = "mega_ball";
        public static final String WINNING_NUMBERS = "winning_numbers";
    }
}
