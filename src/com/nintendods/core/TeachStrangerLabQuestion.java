package com.nintendods.core;

import java.util.Random;

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
