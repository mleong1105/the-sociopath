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
        lunchStart = Util.randomBetween(11, 14) * 100 + Util.randomBetween(0, 60);
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
        try {
            return friends.get(friend);
        } catch (NullPointerException e) {  //no friend link yet
            return 0;
        }
    }

    public int setFriendRep(Student friend, int newRep) {
        try {
            return friends.put(friend, newRep);
        } catch (NullPointerException e) {  //no friend link yet
            addFriend(friend, newRep, 0);
            return 0;
        }
    }

    public Student[] getFriends() {
        HashMap<Student, Integer> copy = new HashMap<>(); //creates a copy so original is not affected
        copy.putAll(friends);

        for (Map.Entry<Student, Integer> element : friends.entrySet()) {
            Student friend = element.getKey();
            Integer rep = element.getValue();

            Student me = new Student();
            for (Map.Entry<Student, Integer> f : friend.friends.entrySet()) {
                if (f.getKey().id == id) {//find self
                    me = f.getKey();
                    break;
                }
            }
            int relativeRep = friend.getFriendRep(me);

            //if rep of either side is less than 1
            if (rep < 1 || relativeRep < 1) {
                copy.remove(rep, friend);
            }
        }
        return copy.keySet().toArray(new Student[0]);
    }
}
