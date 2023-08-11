package com.mialene.hses;

public class Day {
    private int day = 1;
    //day is a level modifier
    //As more days pass, the difficulty increase
    //difficulty affects salad's movespeed, quantity, type, Sarah's workload, time

    public Day() {
        day = 1;
    }

    public int getCurrentDay(){
        return day;
    }
}
