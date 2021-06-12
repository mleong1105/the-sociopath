package com.nintendods.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class HerdImmunity extends Event {
    private Student[] allStudents;
    private int numVaccines;

    public HerdImmunity(Student student, Student[] allStudents, int numVaccines) {
        super(student);

        this.numVaccines = numVaccines;

        // Create copy of all students
        this.allStudents = Arrays.copyOf(allStudents, allStudents.length);
    }

    @Override
    public void execute() {
        // Sort all students array by number of friends
        Arrays.sort(allStudents, Collections.reverseOrder(Comparator.comparingInt(s -> s.getFriends().length)));

        System.out.printf("%d vaccines to be given to students with most friend group connections%n%n", numVaccines);

        // Give vaccine to students with most friend group connections
        for (int i = 0; i < numVaccines; i++) {
            Student vaccinatedStudent = allStudents[i];
            System.out.printf("Vaccine %d goes to student %d with %d friends%n",
                    i + 1, vaccinatedStudent.id, vaccinatedStudent.getFriends().length);
        }
    }
}
