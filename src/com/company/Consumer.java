package com.company;

import java.util.List;

public class Consumer implements Runnable {

    private int DEFAULT_DELAY;
    private final Warehouse warehouse;
    private final List<Unit> tablica;
    private String name;

    public Consumer(String name, List<Unit> tablica, int DEFAULT_DELAY, Warehouse warehouse) {
        this.name = name;
        this.tablica = tablica;
        this.DEFAULT_DELAY = DEFAULT_DELAY;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        int y;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = tablica.size() - 1; i >= 0; i--) {
                    y = warehouse.takeProduct(tablica.get(i).getName(), tablica.get(i).getVolume());
                    System.out.println(name+": took from magazine " + y + "  " + tablica.get(i).getName());
                    tablica.get(i).sub(y);
                    if (tablica.get(i).getVolume() == 0) {
                        tablica.remove(i);
                    }
                    Thread.sleep(DEFAULT_DELAY);
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted!");
                return;
            }
        }
    }

    public void print(){
        System.out.println("\n" + name +"  Left to take from magazine: ");
        for (Unit x : tablica) {
            x.print();
        }
    }
}