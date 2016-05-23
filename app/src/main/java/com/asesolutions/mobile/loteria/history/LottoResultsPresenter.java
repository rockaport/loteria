package com.asesolutions.mobile.loteria.history;

import android.database.Cursor;

import com.asesolutions.mobile.loteria.data.MegaMillionsService;
import com.asesolutions.mobile.loteria.database.Database;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class LottoResultsPresenter implements LottoResultsContract.Presenter {
    private LottoResultsContract.View view;

    LottoResultsPresenter(LottoResultsContract.View view) {
        this.view = view;
    }

    //                 Y/--- fetch --- save-db ---\
    // shouldRefresh ---                           --- get-cursor --- refresh-list --- update-progress
    //                 N\-------------------------/
    @Override
    public void refreshList(boolean forceRefresh) {
        // Start displaying progress
        view.displayProgress(true);

        Observable<Cursor> finalObservable;

        if (forceRefresh) {
            finalObservable = Observable
                    .just(LottoResultsPreferences.shouldRefresh())
                    .flatMap(refresh -> MegaMillionsService.fetchResults())
                    .flatMap(Database::save)
                    .singleOrDefault(null)
                    .flatMap(aVoid -> Database.getMegaMillionsCursor())
                    .delay(2, TimeUnit.SECONDS);
        } else {
            finalObservable = Database.getMegaMillionsCursor();
        }

        finalObservable

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> view.displayProgress(false))
                .subscribe(cursor -> view.showList(cursor),
                        throwable -> view.showError(throwable));
    }
}
