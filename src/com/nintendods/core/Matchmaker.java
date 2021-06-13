package com.nintendods.core;

import java.util.*;

/**ADDITIONAL CHALLENGE 3: MATCHMAKER

 * Gale-Shapley (Stable Matching) Algorithm (https://www.geeksforgeeks.org/stable-marriage-problem/)
 * the event wont run if student has no friends
 * ensure there are an even number of friends (include yourself if odd)
 * assume first half are males, second half are females
 * refer to each student by his/her index in friends array
 * each student has a sorted list of his/her preference to the available opposite sex students
 * run mathing algorithm
 * print out solution
 */

public class Matchmaker extends Event {
    Student[] friends;  //consider only friends of current student
    int[][] preference; //values of rep of males to females(first half) and females to males(second halfs)
    int size;   //total number of males * females (2N)
    int mid;    //number of males which equals to number of females (N)

    public Matchmaker(Student student) {
        super(student);
        friends = student.getFriends();
        ArrayList<Student> tempList = new ArrayList<>();
        Collections.addAll(tempList, friends);
        if (friends.length % 2 == 1) {//you get to pair yourself with someone if your friends are odd number
            tempList.add(student);
        }
        friends = tempList.toArray(friends);    //revert back to array
        size = friends.length;
        mid = friends.length / 2;
    }

    @Override
    public void execute() {
        if (friends.length < 1) {
            System.out.println("Not enough friends to matchmake: minimum 2 friends needed");
            return;
        }
        setPreference();

        int[] matchings = stableMarriage(preference);

        //print out partners
        System.out.printf("\nMatchings by ID:\n%-10s\t%-5s\n", "Female", "Male");
        for (int i = 0; i < matchings.length; i++) {
            System.out.printf("%-10d\t%-5d\n", friends[i + mid].id, friends[matchings[i]].id);
        }
        
        for (int i = 0; i < friends.length; i++) {
            int gainRep =  (100 - friends[i].dive) / 20; //divers give less points
            if (friends[i].id != student.id){//not self
                friends[i].setFriendRep(student, friends[i].getFriendRep(student) + gainRep);
            }
        }
    }

    public void setPreference() {
        preference = new int[friends.length][mid];   //array length [N][N/2]
        //assume first half of students are males, second half are females 
        //create a 2D arrayList of males mapping to females and vice versa, sort by rep
        ArrayList<List<Student>> rep2d = new ArrayList<List<Student>>();
        for (int i = 0; i < friends.length; i++) {
            List<Student> friendRep = new ArrayList<>();
            for (int j = 0; j < mid; j++) {
                if (i < mid) { //male
                    friendRep.add(friends[mid + j]);    //add female friend
                } else { //female
                    friendRep.add(friends[j]);  //add male friend
                }
            }
            friendRep.sort(Comparator.comparingInt(friends[i]::getFriendRep)); //sort by rep
            Collections.reverse(friendRep);  //descending order
            rep2d.add(friendRep);
        }

        List<Student> friendArr = Arrays.asList(friends);
        for (int i = 0; i < friends.length; i++) {
            for (int j = 0; j < mid; j++) {
                //use index of student in friends as reference value
                preference[i][j] = friendArr.indexOf(rep2d.get(i).get(j));
            }
        }
    }

    //Gale-Shapley Algorithm
    public int[] stableMarriage(int[][] prefer) {
        int[] fPartner = new int[mid];
        boolean[] maleEngaged = new boolean[mid];
        Arrays.fill(fPartner, -1);
        int freeCount = mid;
        while (freeCount > 0) {//while there are still free men
            int m;
            for (m = 0; m < mid; m++) {
                if (!maleEngaged[m]) { //not engaged
                    break;
                }
            }
            for (int i = 0; i < mid && !maleEngaged[m]; i++) {
                int f = prefer[m][i];

                if (fPartner[f - mid] == -1) { //female is not engaged
                    fPartner[f - mid] = m; //male and female are engaged
                    maleEngaged[m] = true;
                    freeCount--;
                } else {
                    int m1 = fPartner[f - mid]; //get current engaged male

                    if (fPrefersM(prefer, f, m, m1)) {
                        //f prefers m over m1, break engagement with m1 and get engaged with m
                        fPartner[f - mid] = m;
                        maleEngaged[m] = true;
                        maleEngaged[m1] = false;
                    }
                }
            }
        }
        return fPartner;//returns stable matchings of female to male
    }

    //check which male comes first in the preference of the female
    public boolean fPrefersM(int[][] prefer, int f, int m, int m1) {
        for (int i = 0; i < mid; i++) {
            if (prefer[f][i] == m) {//if m comes first before m1 then f prefers m over m1
                return true;
            }
            if (prefer[f][i] == m1) {//f prefers m1 over m
                return false;
            }
        }
        return false;//both partners not found- error
    }


}
