package com.nintendods.core;

import com.nintendods.util.Util;

/**
 * Event 2: Teaching a stranger how to solve lab questions
 * A student will teach another student
 * Random chance + diving rate determines whether or not successfully taught
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
        student.addFriend(studentBeingTaught, 2, 0);

        // Determine if successfully taught stranger
        if (Util.randomBetween(0, 2) == 0 && Util.randomBetween(0, 100) > studentBeingTaught.dive) {
            studentBeingTaught.setFriendRep(student, studentBeingTaught.getFriendRep(student) + 10);
        }
    }
}
