package com.talesdev.copsandcrims;

import java.util.Scanner;

/**
 * Main class for testing algorithm
 *
 * @author MoKunz
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter angle : ");
        double angle = 0;
        angle = scanner.nextDouble();
        System.out.println("angle : " + getOppositeAngle(angle));
    }

    static double getOppositeAngle(double angle) {
        double newAngle = angle + 180;
        if (newAngle >= 360) {
            newAngle -= 360;
        }
        return newAngle;
    }
}