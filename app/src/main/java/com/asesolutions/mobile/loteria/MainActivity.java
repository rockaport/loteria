package com.asesolutions.mobile.loteria;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.asesolutions.mobile.loteria.data.MegaMillionsApi;
import com.asesolutions.mobile.loteria.data.MegaMillionsApiResult;
import com.asesolutions.mobile.loteria.database.Database;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.results_list)
    RecyclerView resultsList;
    @BindView(R.id.results_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ButterKnife bind views
        ButterKnife.bind(this);

        resultsList.setLayoutManager(new LinearLayoutManager(this));
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

                syncDatabase();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        syncDatabase();


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .build();
//
//        GitHubService service = retrofit.create(GitHubService.class);
    }

    private void syncDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://data.ny.gov/resource/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MegaMillionsApi service = retrofit.create(MegaMillionsApi.class);
                    Call<List<MegaMillionsApiResult>> x = service.listResults("draw_date>'2005-01-01T14:00:00'", 50000);
                    List<MegaMillionsApiResult> results = x.execute().body();
                    Database.save(results);

                    Timber.d(results.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Timber.d("Hey");
                }
            }
        }).start();
    }
}
