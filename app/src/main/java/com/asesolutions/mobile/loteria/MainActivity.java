package com.asesolutions.mobile.loteria;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.asesolutions.mobile.loteria.history.LottoResultsListFragment;
import com.asesolutions.mobile.loteria.home.HomeFragment;
import com.asesolutions.mobile.loteria.tickets.TicketsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        // TODO: don't open the drawer initially
        assert drawerLayout != null;
        drawerLayout.openDrawer(GravityCompat.START);

        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // Quick return if the item is already checked
            if (menuItem.isChecked()) {
                return true;
            }

            handleNavigationItemClicked(menuItem);

            return true;
        });

        // Initialize the home screen
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, new HomeFragment())
                .commit();
    }

    private void handleNavigationItemClicked(MenuItem menuItem) {
        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getFragments().size() > 1) {
            fragmentManager.popBackStackImmediate();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (menuItem.getItemId()) {
            case R.id.home:
                // Do nothing - the pop will take us home
                break;
            case R.id.my_tickets:
                transaction.add(R.id.content_container, new TicketsFragment())
                        .addToBackStack(null);
                break;
            case R.id.history:
                transaction.add(R.id.content_container, LottoResultsListFragment.newInstance())
                        .addToBackStack(null);
                break;
        }

        transaction.commit();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
