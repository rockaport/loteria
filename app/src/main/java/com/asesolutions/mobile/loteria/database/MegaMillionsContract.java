package com.asesolutions.mobile.loteria.database;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.asesolutions.mobile.loteria.data.MegaMillionsApiResult;
import com.asesolutions.mobile.loteria.utils.DateUtil;

import java.text.ParseException;

public class MegaMillionsContract {
    static final String TABLE_NAME = "mega_millions";

    static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    Column._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Column.DRAW_DATE + " INTEGER NOT NULL," +
                    Column.MEGA_BALL + " INTEGER NOT NULL," +
                    Column.WINNING_NUMBERS + " TEXT NOT NULL)";

    static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    static final String[] PROJECTION = {
            Column._ID,
            Column.DRAW_DATE,
            Column.MEGA_BALL,
            Column.WINNING_NUMBERS
    };

    static ContentValues getContentValues(MegaMillionsApiResult result) throws ParseException {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Column.DRAW_DATE, DateUtil.iso8601toUnixTime(result.getDrawDate()));
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
