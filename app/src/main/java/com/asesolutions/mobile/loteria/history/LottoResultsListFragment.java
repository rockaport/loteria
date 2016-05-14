package com.asesolutions.mobile.loteria.history;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asesolutions.mobile.loteria.R;
import com.asesolutions.mobile.loteria.history.adapters.LottoResultsAdapter;
import com.asesolutions.mobile.loteria.utils.AnimUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LottoResultsListFragment extends Fragment implements LottoResultsContract.View {
    private final LottoResultsPresenter presenter;

    @BindView(R.id.results_list)
    RecyclerView resultsList;
    @BindView(R.id.results_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)
    LinearLayout progressLayout;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lotto_results_list, container, false);

        ButterKnife.bind(this, view);

        // Configure the list
        resultsList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configure the swipe refresh layout
        // TODO: source this from some style or resource
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // We're using our own progress ui, so disable this swiperefresh progress
            swipeRefreshLayout.setRefreshing(false);

            presenter.refreshList(true);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO: differentiate between pull down to refresh and the user initially loading this screen
        presenter.refreshList(false);
    }

    @Override
    public void displayProgress(boolean display) {
        if (display) {
            AnimUtil.animateOut(resultsList);
            AnimUtil.animateIn(progressLayout);
        } else {
            AnimUtil.animateOut(progressLayout);
            AnimUtil.animateIn(resultsList);
        }
    }

    @Override
    public void showList(Cursor cursor) {
        resultsList.setAdapter(new LottoResultsAdapter(cursor));
    }

    @Override
    public void showError(Throwable error) {
        Snackbar.make(swipeRefreshLayout, error.getMessage(), Snackbar.LENGTH_LONG).show();
    }
}
