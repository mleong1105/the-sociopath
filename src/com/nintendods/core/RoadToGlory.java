package com.nintendods.core;

import com.nintendods.util.Util;

import java.util.*;

/**
 * Event 3: Road to glory
 * Have lunch with as many people as possible
 * Select random students interested in
 * too lazy to explain...
 */

public class RoadToGlory extends Event {
    private final List<Student> studentsInterested;

    public RoadToGlory(Student student, Student[] allStudents) {
        super(student);

        studentsInterested = new ArrayList<>();

        // Determine students interested in
        for (Student s : allStudents) {
            if (Util.randomBetween(0, 2) == 0)
                studentsInterested.add(s);
        }

        // Sort students by lunchStart time
        studentsInterested.sort(Comparator.comparingInt(o -> o.lunchStart));

        // TODO: Debug only
        studentsInterested.forEach(s1 -> System.out.printf("Student %s = %d : %d min%n", s1.id, s1.lunchStart, s1.lunchPeriod));
    }

    @Override
    public void execute() {
        Map<Integer, Integer> lunchTimes = new LinkedHashMap<>();

        // TODO: Code kinda ugly ngl, clean up if statements
        for (int i = 0; i < studentsInterested.size(); i++) {
            Student s = studentsInterested.get(i);

            // Update friend rep
            s.setFriendRep(student, s.getFriendRep(student) + 1);

            int endTime = Util.reformatTime(s.lunchStart + s.lunchPeriod);

            // Calculate lunch timetable
            if (studentsInterested.size() == 1) {
                lunchTimes.put(s.lunchStart, endTime);
                break;
            }

            if (i == 0) {
                lunchTimes.put(s.lunchStart, Math.min(endTime, studentsInterested.get(i + 1).lunchStart));
            } else if (i == studentsInterested.size() - 1) {
                lunchTimes.put(Math.max(s.lunchStart, studentsInterested.get(i - 1).lunchStart), endTime);
            } else {
                lunchTimes.put(Math.max(s.lunchStart, studentsInterested.get(i - 1).lunchStart),
                        Math.min(endTime, studentsInterested.get(i + 1).lunchStart));
            }
        }

        // Output lunch timetable
        System.out.println("Lunch timetable");

        int i = 0;
        for (Map.Entry<Integer, Integer> entry : lunchTimes.entrySet()) {
            System.out.printf("Student %d = %d -> %d%n", studentsInterested.get(i).id, entry.getKey(), entry.getValue());
            i++;
        }
    }
}
