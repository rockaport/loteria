package com.asesolutions.mobile.loteria.history;

import com.asesolutions.mobile.loteria.data.MegaMillionsService;
import com.asesolutions.mobile.loteria.database.Database;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class LottoResultsPresenter implements LottoResultsContract.Presenter {
    private LottoResultsContract.View view;

    public LottoResultsPresenter(LottoResultsContract.View view) {
        this.view = view;
    }

    @Override
    public void refreshList() {
        view.displayProgress(true);

        Observable.just(LottoResultsPreferences.shouldRefresh())
                .delaySubscription(2, TimeUnit.SECONDS)
                .doOnTerminate(() -> view.displayProgress(false))
                .subscribe(shoudRefresh -> {
                    if (shoudRefresh) {
                        // do the refresh
                        // TODO: 5/13/16 This service should be injected, this should also support
                        // testing
                        MegaMillionsService.syncDatabase();
                        view.showList(Database.getMegaMillionsCursor());
                    }
                });
    }
}
