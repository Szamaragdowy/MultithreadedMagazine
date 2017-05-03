package com.company;

public class Unit {
    private String name;
    private int volume;

    public Unit(String name, int volume) {
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    public void add(int a) {
        this.volume += a;
    }

    public void sub(int a) {
        this.volume -= a;
    }

    public void print() {
        System.out.println("      "+name + ":   " + volume);
    }
}
