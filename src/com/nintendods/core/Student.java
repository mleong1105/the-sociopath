package com.nintendods.core;

import com.nintendods.util.Util;

import java.util.HashMap;
import java.util.Map;

public class Student {
    public final int id;

    public int rep;
    public int dive;
    public final int lunchStart;
    public final int lunchPeriod;

    // Friend / relative rep map
    private final Map<Student, Integer> friends;

    private static int ID_COUNTER = 1;

    public Student() {
        id = ID_COUNTER++;

        rep = Util.randomBetween(0, 10);
        dive = Util.randomBetween(0, 100);
        lunchStart = Math.min(1400, Util.randomBetween(11, 15) * 100 + Util.randomBetween(0, 60));
        lunchPeriod = Util.randomBetween(5, 60);

        friends = new HashMap<>();
    }

    /**
     * @param friend           friend to add
     * @param relativeRep      the reputation from this student to friend being added
     * @param otherRelativeRep the reputation from friend to this student
     */
    public void addFriend(Student friend, int relativeRep, int otherRelativeRep) {
        friends.put(friend, relativeRep);
        friend.friends.put(this, otherRelativeRep);
    }

    public int getFriendRep(Student friend) {
        return friends.get(friend);
    }

    public int setFriendRep(Student friend, int newRep) {
        return friends.put(friend, newRep);
    }

    public Student[] getFriends() {
        return friends.keySet().toArray(new Student[0]);
    }
}
