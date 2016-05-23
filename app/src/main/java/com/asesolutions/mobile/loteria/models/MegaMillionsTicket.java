package com.asesolutions.mobile.loteria.models;

import java.util.Arrays;

public class MegaMillionsTicket {
    public static final int NUM_MEGA = 1;
    private static final int NUM_ENTRIES = 5;
    private int[] numbers;
    private int megaBall;

    public MegaMillionsTicket() {
        numbers = new int[NUM_ENTRIES];
    }

    public int getMegaBall() {
        return megaBall;
    }

    public void setMegaBall(int megaBall) {
        this.megaBall = megaBall;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;

        // TODO: sorting
        Arrays.sort(numbers);
    }
}
