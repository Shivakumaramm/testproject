package com.sample.filtersort.model;

import java.util.Comparator;

public class Item {
    public static Comparator<Item> ascpriceComparator = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            if(o1.getPrice() > o2.getPrice())
                return 1;
            else
                return -1;
        }
    };
    public static Comparator<Item> despriceComparator = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            if(o1.getPrice() < o2.getPrice())
                return 1;
            else
                return -1;
        }
    };
    private String name;
    private String color;
    private Integer size;
    private Double price;

    public Item(String name, String color, Integer size, Double price) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
