package com.asesolutions.mobile.loteria.data;

import com.google.gson.annotations.SerializedName;

public class MegaMillionsApiResult {
    @SerializedName("draw_date")
    String drawDate;
    @SerializedName("mega_ball")
    int megaBall;
    @SerializedName("winning_numbers")
    String winningNumbers;

    public String getDrawDate() {
        return drawDate;
    }

    public int getMegaBall() {
        return megaBall;
    }

    public String getWinningNumbers() {
        return winningNumbers;
    }

    @Override
    public String toString() {
        return "MegaMillionsApiResult{" +
                "drawDate='" + drawDate + '\'' +
                ", megaBall=" + megaBall +
                ", winningNumbers='" + winningNumbers + '\'' +
                '}';
    }
}
