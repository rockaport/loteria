package com.asesolutions.mobile.loteria.tickets;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asesolutions.mobile.loteria.R;
import com.asesolutions.mobile.loteria.addticket.AddTicketActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TicketsFragment extends Fragment {
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    public TicketsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        ButterKnife.bind(this, view);

        floatingActionButton.setOnClickListener(v -> showAddTicketDialog());

        return view;
    }

    private void showAddTicketDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Choose how you will add a ticket")
                .setMessage("Scan doesn't work yet")
                .setNegativeButton("Manual", (dialog, which) -> {
                    getContext().startActivity(new Intent(getContext(), AddTicketActivity.class));
                    Timber.d("Manually add ticket");
                })
                .setPositiveButton("Scan", (dialog, which) -> Timber.d("Scan a ticket"))
                .create()
                .show();
    }
}
