package com.demo;

public class App2 {

    public static void main(String[] args) {
        while (true) {
            System.out.println(getGreeting());
        }
    }

    public static String getGreeting() {
        try {
            Thread.sleep((long) (1000 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
}
