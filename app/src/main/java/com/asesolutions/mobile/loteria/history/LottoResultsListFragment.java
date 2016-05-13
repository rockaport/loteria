package com.asesolutions.mobile.loteria.history;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asesolutions.mobile.loteria.R;
import com.asesolutions.mobile.loteria.database.Database;
import com.asesolutions.mobile.loteria.history.adapters.LottoResultsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LottoResultsListFragment extends Fragment implements LottoResultsContract.View {
    private final LottoResultsPresenter presenter;

    @BindView(R.id.results_list)
    RecyclerView resultsList;
    @BindView(R.id.results_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public LottoResultsListFragment() {
        this.presenter = new LottoResultsPresenter(this);
    }

    public static LottoResultsListFragment newInstance() {
        return new LottoResultsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.refreshList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lotto_results_list, container, false);

        ButterKnife.bind(this, view);

        // Configure the list
        resultsList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configure the swipe refresh layout
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(presenter::refreshList);

        return view;
    }

    @Override
    public void displayProgress(boolean display) {
        swipeRefreshLayout.setRefreshing(display);
    }

    @Override
    public void showList(Cursor cursor) {
        resultsList.setAdapter(new LottoResultsAdapter(Database.getMegaMillionsCursor()));
    }
}
