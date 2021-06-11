package com.nintendods;

import com.nintendods.core.*;
import com.nintendods.util.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ======= INIT STUDENTS  =======

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        Student s4 = new Student();
        Student s5 = new Student();
        Student s6 = new Student();
        Student s7 = new Student();
        Student s8 = new Student();
        Student s9 = new Student();
        Student s10 = new Student();
        Student[] students = {s1, s2, s3, s4, s5, s6, s7, s8, s9, s10};

        s1.addFriend(s2, 5, 8);
        s1.addFriend(s7, 4, 3);
        s2.addFriend(s3, 5, 4);
        s2.addFriend(s5, 6, 2);
        s2.addFriend(s6, 9, 7);
        s4.addFriend(s8, 7, 10);
        s4.addFriend(s10, 7, 7);
        s9.addFriend(s10, 5, 6);

        // ======= GAME LOOP  =======

        CommandParser commandParser = new CommandParser();
        int day = 1;

        Event crush = new Crush();//will affect entire game
        boolean hasCrush = false;

        // Welcome message
        System.out.printf("Welcome to Sociopath. %n%n");

        System.out.print("Choose student to play as [1-10]: ");
        int studentID = commandParser.readInt(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        // 7 days per round
        while (day < 8) {
            // Randomly choose if 2 or 3 events per day
            int eventCount = Util.randomBetween(2, 4);

            System.out.printf("%nDay %d. Choose %d events.%n", day, eventCount);

            while (eventCount > 0) {
                // Get event input choice
                System.out.printf("%nChoose an event:%n");
                System.out.println("1. Teach a stranger a lab question");
                System.out.println("2. Road to glory");
                System.out.println("3. Arranging books");
                System.out.println("4. Crush");
                System.out.println("5. Matchmaker");

                int choice = commandParser.readInt(new int[]{1, 2, 3, 4, 5});

                switch (choice) {
                    case 1: {//teach stranger
                        // Create student id choices
                        List<Integer> studentIDs = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
                        /*
                        // Chose teacher student
                        System.out.println("Select student to be a teacher:");
                        int teachId = commandParser.readInt(studentIDs.stream().mapToInt(i -> i).toArray());
                        */
                        // Cannot chose same students
                        studentIDs.remove(studentID - 1);
                        
                        // Chose taught student
                        System.out.println("Select student to be taught:");
                        int taughtId = commandParser.readInt(studentIDs.stream().mapToInt(i -> i).toArray());

                        // Create and execute event
                        Event e = new TeachStrangerLabQuestion(students[studentID - 1], students[taughtId - 1]);
                        e.execute();

                        break;
                    }
                    case 2: {//road to glory
                        // Create student id choices
                        List<Integer> studentIDs = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

                        // Chose student to dine
                        System.out.println("Select student to dine:");
                        int dineId = commandParser.readInt(studentIDs.stream().mapToInt(i -> i).toArray());

                        // Create and execute event
                        Event e = new RoadToGlory(students[dineId - 1], students);
                        e.execute();

                        break;
                    } 
                    case 3: {//arranging books
                        System.out.println("Arranging Books.");
                        Event e = new ArrangingBooks(students[studentID - 1]);
                        e.execute;
                        break;
                    }
                    case 4: {//initialize crush
                        System.out.println("Crush.");
                        if (hasCrush) {
                            crush.execute();
                        } else {//manually create crush
                            List<Integer> studentIDs = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
                            studentIDs.remove(studentID - 1); //cannot have a crush on yourself

                            for (Integer i : studentIDs) {
                                System.out.print(i + " ");
                            }
                            System.out.print("\nInput student ID of your crush from the list above: ");

                            int crushID = commandParser.readInt(studentIDs.stream().mapToInt(i -> i).toArray());

                            crush = new Crush(students[studentID - 1], students[crushID - 1], students);
                            
                            hasCrush = true;
                        }

                        break;
                    } case 5: {//matchmaker
                        System.out.println("Matchmaker.");
                        Event e = new Matchmaker(students[studentID - 1]);
                        e.execute();
                    }

                    // ...other cases
                }

                // TODO: Press enter to continue & clear console

                // 50% chance event will happen
                if (Util.randomBetween(0, 2) == 0) {
                    System.out.printf("%nRandom Event%n%n");

                    // Determine what event will happen
                    int randEvent = Util.randomBetween(0, 3);

                    switch (randEvent) {
                        case 0: {
                            System.out.println("Herd immunity.");

                            // Chose teacher student
                            System.out.println("Select number of vaccines:");
                            int numVaccines = commandParser.readInt(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

                            Event e = new HerdImmunity(null, students, numVaccines);
                            e.execute();
                            break;
                        } 
                        case 1: {//chitchat
                            System.out.println("Chitchat.");

                            int randIndex = Util.randomBetween(0, students.length);
                            Event e = new ChitChat(students[randIndex]);
                            e.execute();
                            break;
                        } 
                        case 2: {//crush
                            System.out.println("Crush.");
                            if (hasCrush) {
                                crush.execute();
                            } else {//randomly set crush
                                int crushID = studentID - 1;

                                while (crushID == studentID - 1) {//crush cannot be self
                                    crushID = Util.randomBetween(0, students.length);
                                }

                                crush = new Crush(students[studentID - 1], students[crushID], students);

                                hasCrush = true;
                            }
                        }

                        // ...other cases
                    }
                }

                eventCount--;
            }

            day++;
        }

        // ======= TESTING (NOT FINAL)  =======

//        // EVENT 1: TEACH STRANGER
//        // s1 will teach s2
//        Event event1 = new TeachStrangerLabQuestion(s1, s2);
//        event1.execute();
//        // Should print out 2 if couldn't teach
//        // or 12 if taught successfully
//        System.out.println(s1.getFriendRep(s2));
//
//        // EVENT 2: CHITCHAT
//        //passive event: happens to anyone and everyone
//        int N = Util.randomBetween(0, 6);   //let 0-5 chitchats happen every day
//        for (int i = 0; i < N; i++) {
//            int studentID = Util.randomBetween(0, students.length); //might happen to each student
//            Event event2 = new ChitChat(students[studentID]);
//            event2.execute();
//        }
//
//        // EVENT 3: ROAD TO GLORY
//        Event event3 = new RoadToGlory(s1, students);
//        event3.execute();
//
//        //EVENT 5: CRUSH
//        Event event5 = new Crush(s1, s6, students);
//        event5.execute();
//
//        //EVENT 6: FRIENSHIP
//        Event event6 = new Friendship();
//        event6.execute();
//
//        //ADDITIONAL CHALLENGE 3: MATCHMAKER
//        Event matchmaker = new Matchmaker(s2);
//        matchmaker.execute();
//
//        // Additional challenge 6: Herd Immunity
//        Event herdImmunity = new HerdImmunity(null, students, 3);
//        herdImmunity.execute();
//
//        //Additional challenge 7: Ken Thompson
//        Event KenThompson = new KenThompson();
//        KenThompson.execute();
    }
}
