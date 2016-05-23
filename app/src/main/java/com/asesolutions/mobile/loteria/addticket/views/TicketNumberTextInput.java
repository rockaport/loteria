package com.asesolutions.mobile.loteria.addticket.views;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.asesolutions.mobile.loteria.R;
import com.asesolutions.mobile.loteria.utils.MinMaxInputFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

class TicketNumberTextInput extends TextInputLayout {
    @BindView(R.id.number)
    TextInputEditText number;

    public TicketNumberTextInput(Context context) {
        super(context);
        init(context);
    }

    public TicketNumberTextInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TicketNumberTextInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_entry_number, this);

        ButterKnife.bind(this, view);
    }

    String getNumber() {
        return number.getText().toString();
    }

    void setMinMax(int min, int max) {
        number.setFilters(new InputFilter[]{new MinMaxInputFilter(min, max)});
    }

    void addOnFocusListener(OnFocusChangeListener onFocusChangeListener) {
        number.setOnFocusChangeListener(onFocusChangeListener);
    }

    void setImeOptions(int imeOptions) {
        number.setImeOptions(imeOptions);
    }
}
