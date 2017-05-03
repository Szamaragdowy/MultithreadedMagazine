package com.company;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        try {
            Warehouse warehouse = new Warehouse(3, 100, 50);

            Order order1 = new Order("Banana", 650, 1000, warehouse);
            Order order2 = new Order("Apple", 400, 1000, warehouse);
            Order order3 = new Order("Grape", 600, 1000, warehouse);

            Consumer consumer1 = new Consumer("Franek", new LinkedList<>(Arrays.asList(
                    new Unit("Banana", 100),
                    new Unit("Apple", 200),
                    new Unit("Grape", 100))), 500, warehouse);

            Consumer consumer2 = new Consumer("Kamil", new LinkedList<>(Arrays.asList(
                    new Unit("Banana", 300),
                    new Unit("Apple", 200),
                    new Unit("Grape", 200))), 500, warehouse);

            Thread o1 = new Thread(order1);
            Thread o2 = new Thread(order2);
            Thread o3 = new Thread(order3);

            Thread c1 = new Thread(consumer1);
            Thread c2 = new Thread(consumer2);

            o1.start();
            o2.start();
            o3.start();

            c1.start();
            c2.start();

            Thread.sleep(10000);

            o1.interrupt();
            o1.join();
            o2.interrupt();
            o2.join();
            o3.interrupt();
            o3.join();

            c1.interrupt();
            c1.join();
            c2.interrupt();
            c2.join();

            System.out.println("\n----------------- FINAL ANALYSIS -----------------");
            order1.print();
            order2.print();
            order3.print();

            consumer1.print();
            consumer2.print();

            warehouse.print();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

