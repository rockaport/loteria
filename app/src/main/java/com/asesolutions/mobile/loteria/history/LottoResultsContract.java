package com.asesolutions.mobile.loteria.history;

import android.database.Cursor;

public interface LottoResultsContract {
    interface View {
        void displayProgress(boolean display);

        void showList(Cursor cursor);
    }

    interface Presenter {
        void refreshList();
    }
}
