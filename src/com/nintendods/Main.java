package com.nintendods;

import com.nintendods.core.*;
import com.nintendods.util.Util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		// ======= INIT STUDENTS =======

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
		Student[] students = { s1, s2, s3, s4, s5, s6, s7, s8, s9, s10 };

		s1.addFriend(s2, 5, 8);
		s1.addFriend(s7, 4, 3);
		s2.addFriend(s3, 5, 4);
		s2.addFriend(s5, 6, 2);
		s2.addFriend(s6, 9, 7);
		s4.addFriend(s8, 7, 10);
		s4.addFriend(s10, 7, 7);
		s9.addFriend(s10, 5, 6);

		// ======= GAME LOOP =======

		CommandParser commandParser = new CommandParser();
		int day = 1;

		Event crush = new Crush();// will affect entire game
		boolean hasCrush = false;

		// Welcome message
		System.out.printf("Welcome to Sociopath. %n%n");

		System.out.print("Choose student to play as [1-10]: ");
		int studentID = commandParser.readInt(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }) - 1;

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
				System.out.println("6. Friendship (This event will not decrease your events number)");

				int choice = commandParser.readInt(new int[] { 1, 2, 3, 4, 5, 6 });

				switch (choice) {
					case 1: {// teach stranger
						// Create student id choices
						List<Integer> studentIDs = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

						// Cannot chose same student as playing
						studentIDs.remove(studentID);

						// Chose taught student
						System.out.println("Select student to be taught:");
						int taughtId = commandParser.readInt(studentIDs.stream().mapToInt(i -> i).toArray()) - 1;

						// Create and execute event
						Event e = new TeachStrangerLabQuestion(students[studentID], students[taughtId]);
						e.execute();

						break;
					}
					case 2: {// road to glory
						// Create and execute event
						Event e = new RoadToGlory(students[studentID], students);
						e.execute();

						break;
					}
					case 3: {// arranging books
						System.out.println("Arranging Books.");
						Event e = new ArrangingBooks(students[studentID]);
						e.execute();
						break;
					}
					case 4: {// initialize crush
						System.out.println("Crush.");
						if (hasCrush) {
							crush.execute();
						} else {// manually create crush
							List<Integer> studentIDs = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
							studentIDs.remove(studentID); // cannot have a crush on yourself

							for (Integer i : studentIDs) {
								System.out.print(i + " ");
							}
							System.out.print("\nInput student ID of your crush from the list above: ");

							int crushID = commandParser.readInt(studentIDs.stream().mapToInt(i -> i).toArray()) - 1;

							crush = new Crush(students[studentID], students[crushID], students);

							hasCrush = true;
						}
						break;
					}
					case 5: {// matchmaker
						System.out.println("Matchmaker.");
						Event e = new Matchmaker(students[studentID]);
						e.execute();
						break;
					}
					case 6: {// friendship
						System.out.println("Friendship.");
						System.out.println(
								"There is a bonus event that show the theory\"SIX DEGREE OF SEPERATION\", do you want to proceed to it ?");
						System.out.print("Reply (1 for yes, 2 for no): ");
						int reply = commandParser.readInt(new int[] { 1, 2 });
						switch (reply) {
							case 1: {
								System.out.println("\nSix Degree of Seperation.");
								Event e = new KenThompson();
								e.execute();
								break;
							}
							case 2: {
								Event e = new Friendship();
								e.execute();
								break;
							}
						}
						eventCount++;
						break;
					}
				}

				Util.clearScreen();// press enter to clear screen

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
							int numVaccines = commandParser.readInt(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });

							Event e = new HerdImmunity(null, students, numVaccines);
							e.execute();
							break;
						}
						case 1: {// chitchat
							System.out.println("Chitchat.");

							int randIndex = Util.randomBetween(0, students.length);
							Event e = new ChitChat(students[randIndex]);
							e.execute();
							break;
						}
						case 2: {// crush
							System.out.println("Crush.");
							if (hasCrush) {
								crush.execute();
							} else {// randomly set crush
								int crushID = studentID;

								while (crushID == studentID) {// crush cannot be self
									crushID = Util.randomBetween(0, students.length);
								}

								crush = new Crush(students[studentID], students[crushID], students);

								hasCrush = true;
							}
						}
					}

					Util.clearScreen();
				}

				eventCount--;
			}

			// display adjacency matrix at the end of each day
			System.out.printf("End of Day[%d]\n", day);
			Util.display(students);
			Util.clearScreen();

			day++;
		}
	}
}
