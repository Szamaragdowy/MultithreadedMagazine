package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class Warehouse {
    private List<Unit> listOfProducts = new LinkedList<>();
    private int maxTypeCapacity;
    private int maxCapacity;
    private int maxTake;

    Warehouse(int maxTypeCapacity, int maxCapacity, int maxTake) {
        this.maxTypeCapacity = maxTypeCapacity;
        this.maxCapacity = maxCapacity;
        this.maxTake = maxTake;
    }

    synchronized int sendProduct(String product, int quantity) throws InterruptedException {
        for (Unit x : listOfProducts) {
            if (Objects.equals(x.getName(), product)) {
                if ((x.getVolume() + quantity) > maxCapacity) {
                    System.err.println("not enough space to store  " + product + " i will try again with new Capacity");
                    return 0;
                } else {
                    x.add(quantity);
                    System.out.println("Delivered: " + product + "     " + quantity);
                    return quantity;
                }
            }
        }
        if (listOfProducts.size() >= maxTypeCapacity) {
            System.err.println("not enough space to store new kind of product(" + product + ")");
            return 0;
        } else {
            if (quantity > maxCapacity) {
                System.err.println("Delivery is too big to store with actually maxCapacity, i will try again with new Capacity ");
                return 0;
            } else {
                listOfProducts.add(new Unit(product, quantity));
                System.out.println("Delivered: " + product + "     " + quantity);
                return quantity;
            }
        }
    }

    synchronized int takeProduct(String product, int quantity) throws InterruptedException {
        int returnValue;
        for (int i = listOfProducts.size() - 1; i >= 0; i--) {
            if (Objects.equals(listOfProducts.get(i).getName(), product)) {
                if (quantity >= maxTake) {
                    if (listOfProducts.get(i).getVolume() >= maxTake) {
                        listOfProducts.get(i).sub(maxTake);
                        return maxTake;
                    } else {
                        returnValue = listOfProducts.get(i).getVolume();
                        listOfProducts.remove(i);
                        return returnValue;
                    }
                } else {
                    if (listOfProducts.get(i).getVolume() > quantity) {
                        listOfProducts.get(i).sub(quantity);
                        return quantity;
                    } else {
                        returnValue = listOfProducts.get(i).getVolume();
                        listOfProducts.remove(i);
                        return returnValue;
                    }
                }
            }
        }
        return 0;
    }

    void print() {
        System.out.println("\nIn magazine left: ");
        for (Unit x : listOfProducts) {
            x.print();
        }
    }
}