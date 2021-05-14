package com.nintendods.core;

import java.util.Random;

/**
 * Event 2: Teaching a stranger how to solve lab questions
 * A student will teach another student
 * Random chance of determining whether or not successfully taught
 * If successfully taught: rep relative to that person = 10 + 2 (initial rep)
 * If not successfully taught: rep relative is 2 (initial rep)
 */

public class TeachStrangerLabQuestion extends Event {
    Student studentBeingTaught;

    public TeachStrangerLabQuestion(Student student, Student other) {
        super(student);

        studentBeingTaught = other;
    }

    @Override
    public void execute() {
        Random rand = new Random();

        student.addFriend(studentBeingTaught, 2, 0);

        // Randomly determine if successfully taught stranger
        if (rand.nextInt(2) == 0) {
            student.setFriendRep(studentBeingTaught, student.getFriendRep(studentBeingTaught) + 10);
        }
    }
}
