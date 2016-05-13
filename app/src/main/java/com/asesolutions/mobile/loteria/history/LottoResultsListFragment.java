package com.asesolutions.mobile.loteria.history;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LottoResultsListFragment extends Fragment {
    @BindView(R.id.results_list)
    RecyclerView resultsList;
    @BindView(R.id.results_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    public LottoResultsListFragment() {
        // Required empty public constructor
    }

    public static LottoResultsListFragment newInstance() {
        return new LottoResultsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lotto_results_list, container, false);
        ButterKnife.bind(this, view);


        resultsList.setLayoutManager(new LinearLayoutManager(getContext()));
        resultsList.setAdapter(new LottoResultsAdapter(Database.getMegaMillionsCursor()));

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Timber.d("Refreshing");
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

//                syncDatabase();
            }
        });

        return view;
    }
}
