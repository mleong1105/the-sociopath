package com.nintendods.util;

import com.nintendods.core.CommandParser;
import com.nintendods.core.Student;

import java.util.Random;


public class Util {
    private static final Random rand = new Random();

    public static int randomBetween(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    // Ex. given 11:41 and 59 min returns 12:40
    public static int reformatTime(int time, int min) {
        int hours = Integer.parseInt(Integer.toString(time).substring(0, 2));
        int minutes = Integer.parseInt(Integer.toString(time).substring(2, 4)) + min;

        hours += minutes / 60;
        minutes = minutes % 60;

        return Integer.parseInt(hours + (minutes < 10 ? "0" + minutes : String.valueOf(minutes)));
    }

    public static boolean intArrayContains(int[] arr, int v) {
        for (int i : arr) {
            if (v == i)
                return true;
        }

        return false;
    }

    //display graph of student's rep to each other
    public static void display(Student[] students) {
        System.out.print("\nRep points:\n   |");
        for (int i = 0; i < students.length; i++) {
            System.out.printf("#%-2d|", students[i].id);//column label
        }
        for (int i = 0; i < students.length; i++) {
            //grid
            System.out.println();
            for (int j = 0; j < 11; j++) {
                System.out.print("---|");
            }
            System.out.println();

            //row label
            System.out.printf("#%-2d|", students[i].id);
            for (int j = 0; j < students.length; j++) {
                int r = students[i].getFriendRep(students[j]);
                String rep = (r == 0) ? " " : String.valueOf(r);
                System.out.printf("%-3s|", rep);
            }
        }
    }

    public static void clearScreen() {
		try {
            CommandParser cp = new CommandParser();
            System.out.print("\nPress <Enter> to continue: ");
            cp.readString();
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception E) {
			System.out.println(E);
		}
	}
}
