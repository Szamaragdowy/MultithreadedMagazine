/*
Warehause         - klasa magazyn przechowujące produkty wyprodukowane przez klase Order, i udostępniająca zasoby
                        klasie Consumer

maxTypeCapacity   - ilość różnych rodzajów które mogą być przechowywane jednocześnie w magazynie

maxCapacity       - maksymalna ilość produktów danego typu,np 500 bananów, dostawy które przekaczają ten limit
                        oczekują na zwolnienie miejsca

maxTake           - maksymalna ilość pojedynczego produktu którego można zabrać z magazynu np 50 bananów

SendProduct(nazwa produktu, ilość) - sprawdza czy dany produkt jest już w magazynie, jeśli jest sprawdza
                                        czy może dodać taką ilość produktu tzn cyz nie przekroczy maxCapacity,
                                        jeśli oba warunki są spełnione dodaje produkty do aktualnych.
                                     Jeśli produktu nie ma w magazynie, sprawdzane jest czy jest możliwe
                                        utworzenie nowej instancji tzn czy nie zostanie przekroczony limit
                                        maxTypeCapacity, jeśli wszystko jest ok produkt zostaje dodany, jeśli
                                        nie produkcja danego produktu zostaje zatrzymana.

 */
package com.company;

import java.util.LinkedList;
import java.util.List;

public class Warehouse {
    private List<Unit> listOfProducts = new LinkedList<>();
    private int maxTypeCapacity;
    private int maxCapacity;
    private int maxTake;

    public Warehouse(int maxTypeCapacity, int maxCapacity, int maxTake) {
        this.maxTypeCapacity = maxTypeCapacity;
        this.maxCapacity = maxCapacity;
        this.maxTake = maxTake;
    }

    public synchronized int sendProduct(String product, int quantity) throws InterruptedException {
        for (Unit x : listOfProducts) {
            if (x.getName() == product) {
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

    public synchronized int takeProduct(String product, int quantity) throws InterruptedException {
        int returnValue;
        for (int i = listOfProducts.size() - 1; i >= 0; i--) {
            if (listOfProducts.get(i).getName() == product) {
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

    public void print() {
        System.out.println("\nIn magazine left: ");
        for (Unit x : listOfProducts) {
            x.print();
        }
    }
}