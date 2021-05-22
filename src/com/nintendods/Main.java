package com.nintendods;

import com.nintendods.core.*;
import com.nintendods.util.Util;

public class Main {
    public static void main(String[] args) {
        //initialize graph
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        Student s4 = new Student();
        Student s5 = new Student();
        Student s6 = new Student();
        Student s7 = new Student();
        Student s8 = new Student();
        Student s9 = new Student();
        Student s10 = new Student();
        Student[] students = {s1, s2, s3, s4, s6, s7, s8, s9, s10};

        s1.addFriend(s2, 5, 8);
        s1.addFriend(s7, 4, 3);
        s2.addFriend(s3, 5, 4);
        s2.addFriend(s5, 6, 2);
        s2.addFriend(s6, 9, 7);
        s4.addFriend(s8, 7, 10);
        s4.addFriend(s10, 7, 7);
        s9.addFriend(s10, 5, 6);

        // ======= TESTING (NOT FINAL)  =======

        // EVENT 1: TEACH STRANGER
        // s1 will teach s2
        Event event1 = new TeachStrangerLabQuestion(s1, s2);
        event1.execute();
        // Should print out 2 if couldn't teach
        // or 12 if taught successfully
        System.out.println(s1.getFriendRep(s2));

        // EVENT 2: CHITCHAT
        //passive event: happens to anyone and everyone
        int N = Util.randomBetween(0, 6);   //let 0-5 chitchats happen every day
        for (int i = 0; i < N; i++) {
            int studentID = Util.randomBetween(0, students.length); //might happen to each student
            Event event2 = new ChitChat(students[studentID]);
            event2.execute();
        }

        // EVENT 3: ROAD TO GLORY
        Event event3 = new RoadToGlory(s1, students);
        event3.execute();

        //EVENT 5: CRUSH
        Event event5 = new Crush(s1, s6, students);
        event5.execute();
    
        //ADDITIONAL CHALLENGE 3: MATCHMAKER 
        Event matchmaker = new Matchmaker(s2);
        matchmaker.execute();
    }
}
