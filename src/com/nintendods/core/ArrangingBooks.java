package com.nintendods.core;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Event 4: Arranging books in non-increasing order
 * prompt the user to enter number of books
 * store the height input by user into a list
 * compare and remove books that are higher than previous book
 * determine round needed to arrange all books in non-increasing order
 */

public class ArrangingBooks extends Event {

    LinkedList<Integer> height = new LinkedList<>();
    LinkedList<Integer> erase = new LinkedList<>();
    Student student1;

    public ArrangingBooks(Student student) {
        super(student);
    }

    Scanner s = new Scanner(System.in);
    int cnt = 0;
    int books;

    @Override
    public void execute() {

        ArrangingBooks test = new ArrangingBooks(student1);
        test.display();
        for (int i = 0; i < test.getBooks(); i++) {
            test.delete(); //to ensure all numbers that are larger than previous element were removed
        }
        test.output();
    }

    public int getBooks() {
        return books;
    }

    public void display() {
        System.out.print("Enter number of books in a row: ");
        books = s.nextInt(); // get number of books from user
        System.out.print("Enter height of books: ");

        for (int i = 0; i < books; i++) {
            height.add(s.nextInt()); // add height of books entered by user into a linked list
            if (height.get(i).equals(0)) {
                System.exit(0); // exit the program when user input 0
            }
        }
        addErase();
    }

    public void addErase() {
        for (int i = 0; i < height.size() - 1; i++) {
            if (height.get(i + 1) > height.get(i)) {
                erase.add(height.get(i + 1)); //add the element that needed to be remove into another linked list
            }
        }
    }

    public void delete() {
        for (int i = 0; i < height.size() - 1; i++) {
            if (height.get(i + 1) > height.get(i)) {
                height.remove(i + 1); // remove the elements that greater than its previos element
                erase.remove(); // remove the first element in erase linked list
                i = 0; // set i=0 to check for each element inside height again after removing elements
                if (erase.isEmpty()) {
                    cnt++;  // increase number of count when erase is empty
                    addErase(); // call addErase() method to check for element needed to be remove after first round
                    break; // break when no element needed to be remove
                }
            }
        }
    }

    public void output() {
        System.out.println(height);
        System.out.println("Number of round(s) needed: " + cnt);
    }

}
