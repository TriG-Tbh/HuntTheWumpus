
// tons of imports for arrays and array-related stuff
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;

// oh and scanner
import java.util.Scanner;

public class HuntTheWumpus {

	static String wumpusRoom = String.valueOf(randInt(1, 20));
	static String currentRoom = String.valueOf(randInt(1, 20));
	static ArrayList<List<String>> rooms = new ArrayList<List<String>>();

	public static void main(String[] args) {
		// lots of variable declarations
		Scanner input = new Scanner(System.in);
		String action;
		boolean choosing = true;
		int arrows = 5;
		boolean innerLoop;
		String tempRoom;

		// ensuring the player doesn't start in the same room as the wumpus
		while (wumpusRoom.equals(currentRoom)) {
			currentRoom = String.valueOf(randInt(1, 20));
		}

		// populating the rooms list with the adjacent rooms
		// adjacent rooms are accessed through rooms.get(currentRooom)
		// work smarter, not harder
		// thank you, python 3
		rooms.add(Arrays.asList()); // blank list for room 0 (which doesnt exist)
		rooms.add(Arrays.asList("2", "5", "8"));
		rooms.add(Arrays.asList("1", "10", "3"));
		rooms.add(Arrays.asList("2", "12", "4"));
		rooms.add(Arrays.asList("3", "14", "5"));
		rooms.add(Arrays.asList("4", "6", "1"));
		rooms.add(Arrays.asList("15", "5", "7"));
		rooms.add(Arrays.asList("6", "17", "8"));
		rooms.add(Arrays.asList("9", "1", "7"));
		rooms.add(Arrays.asList("8", "18", "10"));
		rooms.add(Arrays.asList("9", "2", "11"));
		rooms.add(Arrays.asList("10", "19", "12"));
		rooms.add(Arrays.asList("11", "3", "13"));
		rooms.add(Arrays.asList("12", "20", "14"));
		rooms.add(Arrays.asList("13", "4", "15"));
		rooms.add(Arrays.asList("14", "6", "16"));
		rooms.add(Arrays.asList("20", "15", "17"));
		rooms.add(Arrays.asList("16", "7", "18"));
		rooms.add(Arrays.asList("17", "9", "19"));
		rooms.add(Arrays.asList("18", "11", "20"));
		rooms.add(Arrays.asList("19", "16", "13"));

		// declaring arraylists for obstacles
		ArrayList<String> pits = new ArrayList<String>();
		ArrayList<String> bats = new ArrayList<String>();
		ArrayList<String> taken = new ArrayList<String>();

		taken.addAll(pits);
		taken.addAll(bats);
		taken.add(wumpusRoom);
		Set<String> unique = new HashSet<String>(taken);

		// ensures that no room has more than 1 type of obstacle (pit + bats wouldn't
		// make sense)
		while (unique.size() < 5) { // 5 unique rooms (1 for Wumpus, 2 for bats, 2 for bottomless pits)
			pits.clear();
			bats.clear();
			taken.clear();

			for (int i = 0; i < 2; i++) {
				pits.add(String.valueOf(randInt(1, 20)));
			}
			for (int i = 0; i < 2; i++) {
				bats.add(String.valueOf(randInt(1, 20)));
			}
			taken.addAll(pits);
			taken.addAll(bats);
			taken.add(wumpusRoom);
			unique = new HashSet<String>(taken);

		}

		// ensures the player doesn't begin in a room with any obstacle
		while (taken.contains(currentRoom)) {
			currentRoom = String.valueOf(randInt(1, 20));
		}

		// more variable declaration
		boolean playing = true;
		boolean wumpusNearby = false;
		boolean pitNearby = false;
		boolean batsNearby = false;
		String adjacentString;
		ArrayList<String> shot = new ArrayList<String>();

		// main game loop
		while (playing) {
			System.out.println("You are currently in room " + currentRoom + ".");

			// even more variable declaration
			wumpusNearby = false;
			pitNearby = false;
			batsNearby = false;
			choosing = true;

			// check if room has obstacle inside
			if (pits.contains(currentRoom)) {
				System.out.println("    The room has a bottomless pit!");
				System.out.println("    You fall in.");
				System.out.println("    Game over!");
				choosing = false;
				playing = false;
			}
			if (bats.contains(currentRoom)) {
				System.out.println("    The room is filled with bats!");
				System.out.println("    They carry you to a random room.");
				choosing = false;
				currentRoom = String.valueOf(randInt(1, 20));
				while (taken.contains(currentRoom)) {
					currentRoom = String.valueOf(randInt(1, 20));
				}
			}

			if (wumpusRoom.equals(currentRoom)) { // if the wumpus is in the current room
				int attacked = randInt(1, 2);
				System.out.println("    The room has a Wumpus!");
				if (attacked == 1) { // if the wumpus picked a 50% chance to attack the player
					System.out.println("    You are attacked by the Wumpus.");
					System.out.println("    Game over!");
					choosing = false;
					playing = false;
				} else {
					System.out.println("    It got scared and ran away...");
					taken.clear();
					taken.addAll(pits);
					taken.addAll(bats);
					while (wumpusRoom.equals(currentRoom) || taken.contains(wumpusRoom)) { // change wumpus room
						wumpusRoom = String.valueOf(randInt(1, 20));
					}

				}
			}

			// arrow firing
			if (shot.contains(wumpusRoom)) {
				System.out.println("    You fire your arrow.");
				System.out.println("    It traverses through the rooms and hits the Wumpus!");
				System.out.println("    You win!");
				choosing = false;
				playing = false;
				shot.clear();
			} else if (shot.contains(currentRoom)) {
				System.out.println("    You fire your arrow.");
				System.out.println("    It traverses through the rooms and hits you!");
				System.out.println("    Game over!");
				choosing = false;
				playing = false;
				shot.clear();
			}
			if (shot.size() > 0) {
				System.out.println("    You fire your arrow.");
				System.out.println("    It traverses through the rooms and hits nothing.");
				System.out.println("    You now have " + arrows + " arrows.");
				shot.clear();
			}

			List<String> adjacent = rooms.get(Integer.parseInt(currentRoom));

			// check if obstacles are nearby
			if (choosing) {
				for (int i = 0; i < adjacent.size(); i++) {
					String room = adjacent.get(i);
					if (pits.contains(room)) {
						pitNearby = true;
					}
					if (bats.contains(room)) {
						batsNearby = true;
					}
					if (wumpusRoom.equals(room)) {
						wumpusNearby = true;
					}
				}
				if (pitNearby) {
					System.out.println("    You feel a breeze coming from a nearby pit.");
				}
				if (batsNearby) {
					System.out.println("    You hear the chirping of nearby bats.");
				}
				if (wumpusNearby) {
					System.out.println("    You smell the foul stench of a nearby Wumpus.");
				}

				List<String> adjacentToJoin = rooms.get(Integer.parseInt(currentRoom));
				adjacentString = String.join(", ", adjacentToJoin);

				System.out.println("The adjacent rooms are " + adjacentString + ".");
				System.out.println("You have " + arrows + " arrows.");
			}
			while (choosing) {
				// main game input section
				System.out.print("Type \"move\" to move to a room, or \"shoot\" to shoot an arrow. ");
				action = input.nextLine().toLowerCase();

				if (action.equals("move") || (action.equals("shoot") && arrows > 0)) {
					if (action.equals("move")) { // move to another room
						innerLoop = true;
						while (innerLoop) {
							System.out.print("Type the room number you want to enter, or \"exit\" to exit. ");
							tempRoom = input.nextLine().toLowerCase();
							if (adjacent.contains(tempRoom) || tempRoom.equals("exit")) {
								if (adjacent.contains(tempRoom)) {
									choosing = false;
									currentRoom = tempRoom;
								}
								innerLoop = false;
							}
						}

					} else if (action.equals("shoot")) { // shoot an arrow
						System.out.println(
								"The arrow can enter up to 5 rooms\nType the room number, \"enter\" to confirm, or \"exit\" to exit.");
						innerLoop = true;
						shot = new ArrayList<String>();
						String current = currentRoom;
						while (innerLoop) {
							List<String> availableShots = rooms.get(Integer.parseInt(current));
							String innerAdjacentString = String.join(", ", availableShots);
							System.out.print(innerAdjacentString + " -> ");
							String roomShot = input.nextLine().toLowerCase();
							if (availableShots.contains(roomShot) || roomShot.equals("enter")
									|| roomShot.equals("exit")) {
								if (availableShots.contains(roomShot)) {
									shot.add(roomShot);
									current = roomShot;
									if (shot.size() >= 5) {
										innerLoop = false;
									}
								} else if (roomShot.equals("exit") || roomShot.equals("enter")) {
									innerLoop = false;
									if (roomShot.equals("enter")) {
										choosing = false;
										arrows -= 1;
									}
								}

							}
						}
					}
				}
			}
			if (playing) {
				System.out.println("---"); // denotes separation of turns
			}
		}
		input.close();

	}

	public static int randInt(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) - min + 2; // generates a random number between two integers
																	// (inclusive)
	}

}