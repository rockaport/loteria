package com.asesolutions.mobile.loteria.addticket;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.asesolutions.mobile.loteria.R;
import com.asesolutions.mobile.loteria.addticket.dialogs.DatePickerFragment;
import com.asesolutions.mobile.loteria.addticket.dialogs.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class AddTicketActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final SimpleDateFormat dateDisplayFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);
    private static final SimpleDateFormat timeDisplayFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;

    @OnClick(R.id.set_date)
    void showDatePicker() {
        new DatePickerFragment().show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.set_time)
    void showTimePicker() {
        new TimePickerFragment().show(getSupportFragmentManager(), "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        ButterKnife.bind(this);

        // Update date and time text displays
        Date date = new Date();

        updateDateDisplayText(date);
        updateTimeDisplayText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        try {
            Date timeSet = new SimpleDateFormat("HH:mm", Locale.US).parse(String.format(Locale.US, "%d:%02d", hour, minute));

            updateTimeDisplayText(timeSet);
        } catch (ParseException e) {
            Timber.d(e, "Crap");
        }
    }

    private void updateTimeDisplayText(Date newTime) {
        time.setText(timeDisplayFormat.format(newTime));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        try {
            Date dateSet = new SimpleDateFormat("yyyy-M-d", Locale.US).parse(String.format(Locale.US, "%d-%d-%d", year, month + 1, day));

            updateDateDisplayText(dateSet);
        } catch (ParseException e) {
            Timber.d(e, "Crap");
        }
    }

    private void updateDateDisplayText(Date newDate) {
        date.setText(dateDisplayFormat.format(newDate));
    }
}
