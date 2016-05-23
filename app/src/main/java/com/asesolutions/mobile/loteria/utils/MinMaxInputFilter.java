package com.asesolutions.mobile.loteria.utils;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxInputFilter implements InputFilter {
    private int min, max;

    public MinMaxInputFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    private boolean isInRange(int c) {
        return c >= min && c <= max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());

            if (isInRange(input)) {
                return null;
            }
        } catch (NumberFormatException ignored) {
        }
        return "";
    }
}
