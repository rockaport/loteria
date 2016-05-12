package com.asesolutions.mobile.loteria;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.openDrawer(GravityCompat.START);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
