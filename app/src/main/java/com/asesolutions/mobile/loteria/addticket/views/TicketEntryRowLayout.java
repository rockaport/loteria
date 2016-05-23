package com.asesolutions.mobile.loteria.addticket.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class TicketEntryRowLayout extends LinearLayout implements View.OnFocusChangeListener {
    // TODO: make this an attribute through xml
    private static final int NUM_ENTRIES = 5;

    private ArrayList<TicketNumberTextInput> ticketNumberTextInputs;
    private TicketNumberTextInput megaBall;
    private boolean[] errors;

    public TicketEntryRowLayout(Context context) {
        super(context);
        init(context);
    }

    public TicketEntryRowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TicketEntryRowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TicketEntryRowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private static ViewGroup.LayoutParams getChildLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
    }

    private void init(Context context) {
        errors = new boolean[NUM_ENTRIES];

        ticketNumberTextInputs = new ArrayList<>(NUM_ENTRIES);

        for (int i = 0; i < NUM_ENTRIES; i++) {
            TicketNumberTextInput ticketNumberTextInput = new TicketNumberTextInput(context);

            ticketNumberTextInput.setMinMax(1, 75);
            ticketNumberTextInput.addOnFocusListener(this);
            ticketNumberTextInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);

            addView(ticketNumberTextInput, getChildLayoutParams());

            ticketNumberTextInputs.add(ticketNumberTextInput);
        }

        // Only one mega ball
        megaBall = new TicketNumberTextInput(context);

        megaBall.setMinMax(1, 15);
        megaBall.addOnFocusListener(this);
        megaBall.setImeOptions(EditorInfo.IME_ACTION_DONE);

        addView(megaBall, getChildLayoutParams());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // Only perform checking when focus is lost to prevent multiple calls
        if (!hasFocus) {
            return;
        }

        // Clear all errors first
        Arrays.fill(errors, false);

        // Validate no duplicates exist
        for (int i = 0; i < NUM_ENTRIES; i++) {
            String first = ticketNumberTextInputs.get(i).getNumber();

            for (int j = i + 1; j < NUM_ENTRIES; j++) {
                String second = ticketNumberTextInputs.get(j).getNumber();

                if (first.isEmpty() || second.isEmpty()) {
                    continue;
                }

                errors[j] |= first.equals(second);
            }
        }

        // Update errors for each number
        for (int i = 0; i < NUM_ENTRIES; i++) {
            if (errors[i]) {
                ticketNumberTextInputs.get(i).setError("Duplicate");
            } else {
                ticketNumberTextInputs.get(i).setError(null);
            }
        }
    }
}
