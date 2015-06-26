package com.talesdev.core;

import java.util.Scanner;

/**
 * @author MoKunz
 */
public class Playground {

    public static void main(String[] args) {
        Problem1 problem1 = new Problem1();
        problem1.start();
    }
}

class Problem1 {
    protected Scanner scanner;
    private int totalCase;
    private int[] numbers;

    public void start() {
        scanner = new Scanner(System.in);
        totalCase = scanner.nextInt();
        numbers = new int[totalCase];
        for (int i = 0; i < totalCase; i++) {
            String s = scanner.next();
            String t = scanner.next();
            int number = reverseSum(s, t);
            numbers[i] = number;
        }
        for (int number : numbers) {
            String output = Integer.toString(number) + " ";
            String reversed = reverseNumberString(Integer.toString(number));
            System.out.println(output + reversed);
        }
    }

    private int reverseSum(String first, String second) {
        return Integer.parseInt(reverseNumberString(first)) + Integer.parseInt(reverseNumberString(second));
    }

    private String reverseNumberString(String numberString) {
        StringBuilder builder = new StringBuilder();
        for (int i = (numberString.length() - 1); i >= 0; i--) {
            builder.append(numberString.charAt(i));
        }
        return builder.toString().replaceFirst("\\b0*", "");
    }
}