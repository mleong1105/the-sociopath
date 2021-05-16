package com.nintendods.core;

import com.nintendods.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Scanner;

/**
 * Event 5: Meet your crush
 * Rumour will begin in a cluster that does not contain crush
 * Stop the rumour from reaching crush
 * Can convince max 1 person per day
 * Good end: rumour dies out
 * Bad end: crush knows rumour
 */

public class Crush extends Event {
    Student crush;
    int[] cluster1 = {1,2,3,4,6,7};
    int[] cluster2 = {4,8,9,10};
    static ArrayList<Student> knowsRumour = new ArrayList<>();               //list of students who know the rumour
    static ArrayList<Student> convinced = new ArrayList<>();                 //students who are immune to the rumour
    static ArrayList<Student> toConvince = new ArrayList<>();                //students who need to be convinced
    
    public Crush(Student student, Student crush, Student[] allStudents){
        super(student);
        this.crush = crush;
        for (Student std : allStudents) {   //add everyone except crush and main protagonist
            if (std.id != crush.id) {
                toConvince.add(std);
            }
        }
        toConvince.remove(student);
    }

    @Override
    public void execute() {
    //run everyday
        spreadRumour();
        if (knowsRumour.isEmpty()) {   //End state 1: rumour is gone
            System.out.println("Rumour is gone- Good End");
            return;
        } else if (knowsRumour.contains(crush)){    //End State 2: crush knows rumour
            System.out.println("Crush knows rumour- Bad End");
            int crushRep = crush.getFriendRep(student);
            crushRep -= 10;
            crush.setFriendRep(student, crushRep);      //penalty: he/she hates you now
            return;
        } else {    //convince by priority
            /*TODO: user may choose to NOT convince people on that day
            Scanner input = new Scanner(System.in);
            System.out.print("Do you want to convince peoeple today? (y/n): ");
            String ans = input.nextLine();
            input.close();
            if (ans.equalsIgnoreCase("y")) {
                convince(toConvince.get(0));
            }*/
            convince(toConvince.get(0));
        }
    }

    //run once only
    public void initializeRumour() {
        int randInt;
        int crushCluster = inCluster(crush);
        if (crushCluster == 1) {    //rumour starts from cluster 2
            randInt = Util.randomBetween(0, cluster2.length);
        } else {                    //rumour starts from cluster 1
            randInt = Util.randomBetween(0, cluster1.length);
        }
        //randomize beginner of rumour
        for (Student student : toConvince) {
            if (student.id == randInt){
                knowsRumour.add(student);
                break;
            }
        }
        Student link1 = new Student();;
        randInt = Util.randomBetween(0, cluster1.length);
        for (Student student : toConvince) {
            if (student.id == randInt) {
                link1 = student;
                break;
            }
        }
        Student link2 = new Student();;
        randInt = Util.randomBetween(0, cluster2.length);
        for (Student student : toConvince) {
            if (student.id == randInt) {
                link2 = student;
                break;
            }
        }
        link1.addFriend(link2, 5, 5);   //created a link between 2 clusters
        
        //sort all students by number of friends
        toConvince.sort(Comparator.comparingInt(o -> o.getFriends().length));
        Collections.reverse(toConvince);
        //prioritize the students who link the 2 clusters
        toConvince.remove(link1);
        toConvince.remove(link2);
        toConvince.add(0, link1);
        toConvince.add(0, link2);
    }

    public int inCluster(Student student) {
        for (int i = 0; i < cluster2.length; i++) {
            if (cluster2[i] == student.id) {
                return 2;
            }
        }
        return 1;
    }

    public void convince(Student student) {
        //TODO: make convincing more difficult by considering dive and rep
        if (knowsRumour.contains(student)) {
            knowsRumour.remove(student);
        }
        convinced.add(student);
        toConvince.remove(student);
    }

    public void spreadRumour() {
        for (Student s : knowsRumour) {
            for (Student friend : s.getFriends()) {
                if (!knowsRumour.contains(friend) && !convinced.contains(friend)) {
                    knowsRumour.add(friend);
                    break;
                }
            }
        }
    }
}
