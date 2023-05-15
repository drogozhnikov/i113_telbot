package com.telbot.utils;

public class DiseRoller {

    public String roll(int count) {
        count++;
        int min = 1;
        int roll = (int) (Math.random() * (count - min) + min);
        return String.valueOf(roll);
    }
}
