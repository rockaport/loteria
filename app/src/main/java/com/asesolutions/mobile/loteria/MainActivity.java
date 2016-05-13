package com.asesolutions.mobile.loteria;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.asesolutions.mobile.loteria.data.MegaMillionsApi;
import com.asesolutions.mobile.loteria.data.MegaMillionsApiResult;
import com.asesolutions.mobile.loteria.database.Database;
import com.asesolutions.mobile.loteria.history.LottoResultsListFragment;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_container)
    FrameLayout contentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ButterKnife bind views
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        assert drawerLayout != null;
        drawerLayout.openDrawer(GravityCompat.START);

        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        handleNavigationItemClicked(menuItem);
                        return true;
                    }
                });
    }

    private void handleNavigationItemClicked(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.history:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content_container, LottoResultsListFragment.newInstance())
                        .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.history:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content_container, LottoResultsListFragment.newInstance())
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
