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
        System.out.print("Enter your angle : ");
        double x = scanner.nextDouble();
        System.out.print("Enter victim angle : ");
        double angle = scanner.nextDouble();
        boolean isAngleBetween = isAngleBetween(x, normalizeAngle(angle - 20), normalizeAngle(angle + 20));
        System.out.println(isAngleBetween);
    }

    private static boolean isAngleBetween(double target, double angle1, double angle2) {
        // make the angle from angle1 to angle2 to be <= 180 degrees
        double rAngle = ((angle2 - angle1) % 360 + 360) % 360;
        if (rAngle >= 180) {
            // swap
            double tmp = angle1;
            angle1 = angle2;
            angle2 = tmp;
        }
        // check if it passes through zero
        if (angle1 <= angle2)
            return target >= angle1 && target <= angle2;
        else
            return target >= angle1 || target <= angle2;
    }

    private static double normalizeAngle(double angle) {
        if (angle >= 360) {
            return angle - 360;
        } else if (angle <= 0) {
            return angle + 360;
        } else {
            return angle;
        }
    }
}