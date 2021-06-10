package com.nintendods.core;

import com.nintendods.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


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

    public Crush(){
        super(new Student());
    }
    
    public Crush(Student student, Student crush, Student[] allStudents){
        super(student);
        this.crush = crush;
        for (int i = 0; i < allStudents.length; i++) {   //add everyone except crush and main protagonist
            if (allStudents[i].id != crush.id) {
                toConvince.add(allStudents[i]);
            }
        }
        toConvince.remove(student);

        initializeRumour();  //rumour pops out from nowhere
    }

    @Override
    public void execute() {
        //run everyday
        if (knowsRumour.isEmpty() || toConvince.isEmpty()) {   //End state 1: rumour is gone
            System.out.println("Rumour is gone- Good End");
            return;
        } else if (knowsRumour.contains(crush)) {    //End State 2: crush knows rumour
            System.out.println("Crush knows rumour- Bad End");
            int crushRep = crush.getFriendRep(student);
            crushRep -= 10;
            crush.setFriendRep(student, crushRep);      //penalty: he/she hates you now
            return;
        } else {    //convince by priority
            spreadRumour();
            //user may choose to NOT convince people on that day
            System.out.printf("%d people are not yet immune to the rumour. Do you want to convince peoeple today? (y/n): ",
             toConvince.size());
            CommandParser cp = new CommandParser();
            char ans = cp.readChar(new char[]{'y', 'Y', 'n', 'N'});
            Character.toLowerCase(ans);
            if (ans == 'y') {
                convince(toConvince.get(0));
            } else {
                System.out.println("You did not convince anyone today.");
            }
        }
    }

        //run once only
        public void initializeRumour() {
            int randInt;
            int crushCluster = inCluster(crush);
            if (crushCluster == 1) {    //rumour starts from cluster 2
                randInt = cluster2[Util.randomBetween(0, cluster2.length)];
            } else {                    //rumour starts from cluster 1
                randInt = cluster1[Util.randomBetween(0, cluster1.length)];
            }
            //randomize beginner of rumour
            for (int i = 0; i < toConvince.size(); i++) {
                if (toConvince.get(i).id == randInt){
                    knowsRumour.add(toConvince.get(i));
                    System.out.printf("\nStudent[%d] has started a rumour that you have a crush on Student[%d]\n\n",
                            toConvince.get(i).id, crush.id);
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
            //students with dive > 50 might not get convinced so easily 
            boolean success = true;
            int chance = Util.randomBetween(0, 100) + 50;
            if (student.dive > chance) {
                success = false;
            }

            if (success) {  
                if (knowsRumour.contains(student)) {
                    knowsRumour.remove(student);
                }
                convinced.add(student);
                toConvince.remove(student);
                System.out.printf("\nConvinced Student[%d]\n", student.id);
            } else {
                System.out.printf("\nFailed to convince Student[%d]\n", student.id); 
                //move this student to last index in priority list
                toConvince.remove(student);
                toConvince.add(student);
            }
        }

        public void spreadRumour() {
            ArrayList<Student> newToRumour = new ArrayList<>();
            for (int i = 0; i < knowsRumour.size(); i++) {
                Student[] friends = knowsRumour.get(i).getFriends();
                for (int j = 0; j < friends.length; j++) {
                    if (!knowsRumour.contains(friends[j]) && !convinced.contains(friends[j])) {
                        //if not convinced nor knows rumour, add to knows rumour
                        newToRumour.add(friends[j]);
                        System.out.printf("Student[%d] told Student[%d] about the rumour\n",
                                knowsRumour.get(i).id, friends[j].id);
                        break;
                    }
                }
            }
            knowsRumour.addAll(newToRumour);
        }
    }
