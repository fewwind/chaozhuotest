package com.example.fewwind.chaozhuofirst.bean;

/**
 * Created by fewwind on 18-5-3.
 */

public class LearnUITypeBwan {
    public static final int Activity = 0;
    public static final int Fragment = 1;

    public int type;
    public Class<?> clazz;

    public LearnUITypeBwan(int type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }
}
