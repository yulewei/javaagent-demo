package com.demo;

public class App {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println(getGreeting());
            Thread.sleep(1000L);
        }
    }

    public static String getGreeting() {
        return "hello world";
    }
}
