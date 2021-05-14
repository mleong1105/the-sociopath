package com.nintendods.core;

import com.nintendods.util.Util;

/**
 * Event 2: Chitchat (Passive Event)
 * randomly generate a friend of A (Student B)
 * randomly generate a friend of B (Student C)
 * randomly generate a number between 0 and 100 inclusive (randInt)
 * get the reputation points of A relative to B (repAC)
 * get the reputation points of A relative to B (repAB)
 * get the reputation points of B relative to A (repBA)
 * get the dive probability of B (diveB)
 * if randInt is lesser than (10*repBA - 0.5*diveB) it is a good message, else it is a bad message
 * if good message, repAC = repAC + 0.5*repAB
 * if bad message, repAC = repAC - repAB
 */

public class ChitChat extends Event {
    Student studentB;
    Student studentC;

    public ChitChat(Student student) {
        super(student);
    }

    @Override
    public void execute() {
        //TODO: solution for possible infinite loop if A and B are only friends with each other 
        do {
            studentB = generateRandomFriend(student);
        } while (studentB.getFriends().length < 2); //student B must have at least 2 friends

        do {
            studentC = generateRandomFriend(studentB);
        } while (studentC.id == student.id);    //student C cannot be student A

        int repAB = student.getFriendRep(studentB);
        int repBA = studentB.getFriendRep(student);
        int repAC = student.getFriendRep(studentC);
        int diveB = studentB.dive;
        int probability = Math.max(0, repBA * 10 - diveB / 2);  //capped at 0
        int randInt = Util.randomBetween(0, 101);

        System.out.printf("%nStudent[%d] chatted with Student[%d] about Student[%d]%n",
                studentB.id, studentC.id, student.id);       //B chatted to C about A
        if (randInt < probability) {
            repAC += repAB / 2;
            System.out.println("Good message");
        } else {
            repAC -= repAB;
            System.out.println("Bad message");
        }
        student.setFriendRep(studentC, repAC);
        System.out.printf("Current rep points of Student[%d] to Student[%d]: %d%n",
                student.id, studentC.id, repAC);
    }

    public Student generateRandomFriend(Student student) {
        int randInt = Util.randomBetween(0, student.getFriends().length);
        return student.getFriends()[randInt];
    }
}
