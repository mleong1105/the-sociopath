package com.nintendods;

import com.nintendods.core.Event;
import com.nintendods.core.Student;
import com.nintendods.core.TeachStrangerLabQuestion;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student();

        // s1 will teach s2
        Event event1 = new TeachStrangerLabQuestion(s1, s2);
        event1.execute();

        // Should print out 2 if couldn't teach
        // or 12 if taught successfully
        System.out.println(s1.getFriendRep(s2));
    }
}
