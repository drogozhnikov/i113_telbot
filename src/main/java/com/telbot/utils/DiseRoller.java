package com.telbot.utils;

public class DiseRoller {

    public String roll(int count){
        count++;
        int roll = (int) (Math.random()*count);
        return String.valueOf(roll);
    }
}
