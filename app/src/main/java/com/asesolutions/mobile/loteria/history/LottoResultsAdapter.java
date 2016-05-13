package com.asesolutions.mobile.loteria.history;

import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asesolutions.mobile.loteria.DateUtil;
import com.asesolutions.mobile.loteria.MainApplication;
import com.asesolutions.mobile.loteria.R;
import com.asesolutions.mobile.loteria.database.MegaMillionsContract;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LottoResultsAdapter extends RecyclerView.Adapter<LottoResultsAdapter.ViewHolder> {
    private static final SimpleDateFormat dateDisplayFormat =
            new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);

    long currentTime;
    Cursor resultsCursor;
    int ballColor;
    int extraBallColor;

    public LottoResultsAdapter(Cursor resultsCursor) {
        this.resultsCursor = resultsCursor;
        this.currentTime = System.currentTimeMillis();
        this.ballColor = MainApplication.getStaticContext().getResources().getColor(R.color.colorPrimary);
        this.extraBallColor = MainApplication.getStaticContext().getResources().getColor(R.color.colorAccent);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get an instance of the layout inflater
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the row
        View view = layoutInflater.inflate(R.layout.lotto_result_row, parent, false);

        // Get the container where numbers will be placed
        LinearLayout numbersContainer = (LinearLayout) view.findViewById(R.id.numbers_container);

        // Loop through and add lotto balls
        // TODO: shouldn't hardcode the number of balls to display
        for (int i = 0; i < 6; i++) {
            layoutInflater.inflate(R.layout.lotto_result_number_ball, numbersContainer, true);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        resultsCursor.moveToPosition(holder.getAdapterPosition());

        // Update the draw date
        long drawTime = resultsCursor.getLong(resultsCursor.getColumnIndex(MegaMillionsContract.Column.DRAW_DATE));

        holder.drawDate.setText(dateDisplayFormat.format(DateUtil.unixToDate(drawTime)));

        // Update the elapsed time
        long duration = currentTime - drawTime;

        if (duration >= DateUtils.YEAR_IN_MILLIS) {
            long years = duration / DateUtils.YEAR_IN_MILLIS;
            holder.dateAgo.setText(String.format(Locale.US, "%d year%s ago", years, (years > 1) ? "s" : ""));
        } else if (duration >= DateUtils.WEEK_IN_MILLIS) {
            holder.dateAgo.setText(DateUtils.getRelativeTimeSpanString(drawTime, currentTime, DateUtils.WEEK_IN_MILLIS));
        } else {
            holder.dateAgo.setText(DateUtils.getRelativeTimeSpanString(drawTime, currentTime, DateUtils.DAY_IN_MILLIS));
        }

        // Update the actual numbers
        String[] winningNumbers = resultsCursor.getString(resultsCursor.getColumnIndex(MegaMillionsContract.Column.WINNING_NUMBERS)).split(" ");

        for (int i = 0; i < winningNumbers.length; i++) {
            View numberBall = holder.numbersContainer.getChildAt(i).findViewById(R.id.lotto_ball_number);

            ((TextView) numberBall).setText(winningNumbers[i]);
            setBackgroundColor(numberBall, ballColor);
        }

        int megaBall = resultsCursor.getInt(resultsCursor.getColumnIndex(MegaMillionsContract.Column.MEGA_BALL));

        View numberBall = holder.numbersContainer.getChildAt(holder.numbersContainer.getChildCount() - 1).findViewById(R.id.lotto_ball_number);

        ((TextView) numberBall).setText(String.valueOf(megaBall));
        setBackgroundColor(numberBall, extraBallColor);
    }

    private void setBackgroundColor(View view, int color) {
        GradientDrawable drawable = (GradientDrawable) view.getBackground();
        drawable.setColor(color);
    }

    @Override
    public int getItemCount() {
        if (resultsCursor == null || resultsCursor.isClosed()) {
            return 0;
        }

        return resultsCursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.draw_date)
        TextView drawDate;
        @BindView(R.id.date_ago)
        TextView dateAgo;
        @BindView(R.id.numbers_container)
        LinearLayout numbersContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
