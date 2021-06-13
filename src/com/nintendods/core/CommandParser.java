package com.nintendods.core;

import com.nintendods.util.Util;

import java.util.Scanner;

public class CommandParser {
    public final Scanner scn;

    public CommandParser() {
        scn = new Scanner(System.in);
    }

    public int readInt(int[] choices) {
        while (true) {
            try {
                int v = getIntInput();

                if (Util.intArrayContains(choices, v)) {
                    return v;
                } else {
                    System.out.println("Invalid choice. Chose again.");
                }
            } catch (Exception ignored) {
                System.out.println("Invalid format. Try again.");
                scn.next();
            }
        }
    }

    private int getIntInput() {
        System.out.print("> ");
        return scn.nextInt();
    }

    //Y/N input
    public char readChar(char[] choices) {
        while (true) {
            try {
                System.out.print("> ");
                char v = scn.nextLine().charAt(0);

                for (char c : choices) {
                    if (c == v) {
                        return v;
                    }
                }
                System.out.println("Invalid choice. Chose again.");
            } catch (Exception ignored) {
                System.out.println("Invalid format. Try again.");
                scn.nextLine();
            }
        }
    }

    public String readString() {
        System.out.print("> ");
        String s = scn.nextLine();
        return s;
    }

}
