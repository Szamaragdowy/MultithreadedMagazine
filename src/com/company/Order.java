package com.company;

import java.util.Random;

public class Order implements Runnable {
    private final Random random = new Random();
    private final Warehouse warehouse;
    private int DEFAULT_DELAY;
    private String product;
    private int defaultAmount;
    private int toDo;

    public Order(String product, int defaultAmount, int DEFAULT_DELAY, Warehouse warehause) {
        this.product = product;
        this.defaultAmount = this.toDo = defaultAmount;
        this.DEFAULT_DELAY = DEFAULT_DELAY;
        this.warehouse = warehause;
    }

    @Override
    public void run() {
        int x;
        int y;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (toDo > 100) {
                    x = random.nextInt(99) + 1;
                    y = warehouse.sendProduct(product, x);
                    if (y > 0) {
                        toDo -= y;
                    } else {
                        Thread.sleep(1000);
                    }
                } else {
                    warehouse.sendProduct(product, toDo);
                    toDo = 0;
                    return;
                }
                Thread.sleep(DEFAULT_DELAY);
            } catch (InterruptedException e) {
                System.out.println("Order interrupted!(" + product + ")");
                return;
            }
        }
    }

    public void print() {
        System.out.println("\nProduced " + (defaultAmount - toDo) + "/" + defaultAmount + "   " + product);
    }
}