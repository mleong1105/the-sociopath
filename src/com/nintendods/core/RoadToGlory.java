package com.nintendods.core;

import com.nintendods.util.Util;

import java.util.*;

// Event3: Road To Glory

public class RoadToGlory extends Event {
    private final List<Student> studentsInterested;

    public RoadToGlory(Student student, Student[] allStudents) {
        super(student);

        System.out.printf("Student %d is having lunch%n%n", student.id);

        studentsInterested = new ArrayList<>();

        // Determine students interested in
        for (Student s : allStudents) {
            if (Util.randomBetween(0, 2) == 0 && s.id != student.id)
                studentsInterested.add(s);
        }

        // Sort students by lunchStart time
        studentsInterested.sort(Comparator.comparingInt(o -> o.lunchStart));

        System.out.println("Students interested in lunch times:");
        studentsInterested.forEach(s1 -> System.out.printf("Student %s = %d - %d (%d min)%n",
                s1.id, s1.lunchStart, Util.reformatTime(s1.lunchStart, s1.lunchPeriod), s1.lunchPeriod));
    }

    // TODO: Include times students came to and left the table
    @Override
    public void execute() {
        System.out.printf("%nLunch timeline:%n");

        Deque<Student> studentsOnTable = new ArrayDeque<>();

        int repGained = 0;
        for (Student s : studentsInterested) {
            // Check if seated students should leave table
            for (Student seatedStudent : studentsOnTable) {
                if (s.lunchStart > Util.reformatTime(seatedStudent.lunchStart, seatedStudent.lunchPeriod)) {
                    studentsOnTable.remove(seatedStudent);
                    System.out.printf("Student %d left the table, ", seatedStudent.id);
                }
            }

            // Don't add student if table full
            if (studentsOnTable.size() >= 3)
                continue;

            // Add student to table
            studentsOnTable.push(s);
            s.setFriendRep(student, s.getFriendRep(student) + 1);
            repGained++;

            System.out.printf("Student %d came to the table%n", s.id);
        }

        System.out.printf("%nLunch finished. Rep gained: %d%n", repGained);
    }
}
