package com.nintendods;

import com.nintendods.core.Student;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student();

        s1.addFriend(s2, 4, 5);

        System.out.println(s2.getFriendRep(s1));
    }
}
