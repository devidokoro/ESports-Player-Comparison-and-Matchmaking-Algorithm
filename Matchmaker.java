package hello;
import java.util.*;

public class Matchmaker {
	private static final int TEAM_SIZE = 5;
	private static final int MAX_ELO = 5000;
	private static final int ELO_TOLERANCE = 50;
	
	private Random gen = new Random();
	
	//generate a team
	public ArrayList<ESportsPlayer> generateTeam() {
		ArrayList<ESportsPlayer> team = new ArrayList<ESportsPlayer>();
		for (int i = 0; i < TEAM_SIZE; i++) {
			int elo = 500 + gen.nextInt(MAX_ELO - 500 + 1); //min elo 500, max elo 5000
			int games = 1 + gen.nextInt(10); //random games
			int avgKills = 10 + gen.nextInt(16); //10-25 random kills
			int kills = avgKills * games;
			
			team.add(new ESportsPlayer("Player" + (i + 1), elo, kills, games));
		}
		return team;
	}
	
	//calculate total ELO of a team
	public int calculateTeamELO(ArrayList<ESportsPlayer> team) {
		int total = 0;
		for (int i = 0; i < team.size(); i++) {
			ESportsPlayer p = (ESportsPlayer) team.get(i);
			total += p.getELO();
		}
		return total;
	}
	//find a match
	public void findMatch(ESportsPlayer user) {
		int attempts = 0;
		while (true) {
			attempts += 1;
			//team A, includes the real player
			ArrayList<ESportsPlayer> teamA = generateTeam();
			teamA.set(0, user); //replace player1 with the real player
			
			//team B, is random
			ArrayList<ESportsPlayer> teamB = generateTeam();
			
			int eloA = calculateTeamELO(teamA);
			int eloB = calculateTeamELO(teamB);
			
			if (Math.abs(eloA - eloB) <= ELO_TOLERANCE) {
				System.out.println("Match found after " + attempts + " attempts!");
				System.out.println("Team A (ELO: " + eloA + "): " + teamA);
				System.out.println("Team B (ELO: " + eloB + "): " + teamB);
				break;	
			}
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		//sample of top players
		ESportsPlayer[] team = {
				new ESportsPlayer("Twistzz", 3500, 185, 10),
				new ESportsPlayer("Donk", 5000, 205, 10),
				new ESportsPlayer("Zyw0o", 4300, 195, 10)
		};
		
		System.out.println("Top CS2 players: ");
		System.out.println("=================");
		for (ESportsPlayer esp : team) {
			System.out.println(esp);
		}
		System.out.println("=================");
		
		//USER STATS INPUT
		System.out.println("How do you match up against CS2's top players?");
		System.out.println("Enter your name: ");
		String nameAns = scan.nextLine();
		
		System.out.println("Enter your elo: ");
		int eloAns = scan.nextInt();
		while (eloAns < 500) {
			System.out.println("Enter an elo of 500 or higher: ");
			eloAns = scan.nextInt();
		}
		
		System.out.println("Enter your total lifetime kills: ");
		int killsAns = scan.nextInt();
		
		System.out.println("Enter total lifetime games you have played: ");
		int gamesAns = scan.nextInt();
		scan.nextLine();
		
		ESportsPlayer user = new ESportsPlayer(nameAns, eloAns, killsAns, gamesAns);
		
		System.out.println("\nYour stats: ");
		System.out.println(user);
		
		//COMPARISON
		int eloSubDiff1 = team[0].getELO() - eloAns; //user's elo compared to Twistzz
		int eloSubDiff2 = team[1].getELO() - eloAns; //Donk
		int eloSubDiff3 = team[2].getELO() - eloAns; //Zyw0o
		
		int eloAddDiff1 = eloAns - team[0].getELO();
		int eloAddDiff2 = eloAns - team[1].getELO();
		int eloAddDiff3 = eloAns - team[2].getELO();
		
		if (eloAns < team[0].getELO()) {
			System.out.println("You have " + eloSubDiff1 + " less elo than " + team[0].getName() + ".");
		} else if (eloAns > team[0].getELO()) {
			System.out.println("You have " + eloAddDiff1 + " more elo than " + team[0].getName() + ".");
		} else {
			System.out.println("You and " + team[0].getName() + " have the same elo rating!");
		}
		
		if (eloAns < team[1].getELO()) {
			System.out.println("You have " + eloSubDiff2 + " less elo than " + team[1].getName() + ".");
		} else if (eloAns > team[1].getELO()) {
			System.out.println("You have " + eloAddDiff2 + " more elo than " + team[1].getName() + ".");
		} else {
			System.out.println("You and " + team[1].getName() + " have the same elo rating!");
		}
		
		if (eloAns < team[2].getELO()) {
			System.out.println("You have " + eloSubDiff3 + " less elo than " + team[2].getName() + ".");
		} else if (eloAns > team[1].getELO()) {
			System.out.println("You have " + eloAddDiff3 + " more elo than " + team[2].getName() + ".");
		} else {
			System.out.println("You and " + team[2].getName() + " have the same elo rating!");
		}
		
		double threshold = 9.0; //average kills threshold
		int numAboveTarget = ESportsPlayer.topPlayers(team, threshold);
		int betterPlayers = ESportsPlayer.topPlayers(team, user.getAverageKills());
		System.out.println("\nTop players with higher average kills than you: " + betterPlayers);
		System.out.println(numAboveTarget + " of the top players possess a kill average that is greater than " + threshold + " kills per game");
		
		if (user.getAverageKills() > threshold) {
			System.out.println("You also possess a kill average that is greater than " + threshold + " kills per game");
		} else {
			System.out.println("You however, possess a kill average that is less than " + threshold + " kills per game");
		}
		System.out.println();
		
		
		//REGION + QUEUE + MATCHMAKING
		Matchmaker matchmaker = new Matchmaker();
		
		System.out.println("=== Welcome to the Matchmaking Simulator ===");
		String[] regions = {"NEW YORK", "CHICAGO", "SAN FRANCISCO", "MIAMI", "HOUSTON"};
		
		for (int i = 0; i < regions.length; i++) {
			System.out.println((i + 1) + ". " + regions[i]);
		}
		System.out.println("Enter the number of your preferred geographical region: ");
		int choice = 0;
		while (true) {
			if (scan.hasNextInt()) {
				choice = scan.nextInt();
				if (choice >= 1 && choice <= 5) {
					break;
				}
			}
			System.out.println("Invalid input, enter a number (1-5): ");
			scan.nextLine();
		}
		String regionAnswer = regions[choice - 1];
		scan.nextLine();
		
		System.out.println("Press ENTER to enter the queue...");
		
		//wait for ENTER key
		scan.nextLine();
		
		System.out.println("Finding a fair match... please wait.\n");
		matchmaker.findMatch(user);
		
		System.out.println("Match successfully created!\n");
		
		
		System.out.println("Do you accept this match? yes/no: ");
		String answer2 = scan.nextLine();
		
		while (!answer2.equalsIgnoreCase("yes") && !answer2.equals("no")) {
			System.out.println("Answer either yes or no please.");
			answer2 = scan.nextLine();
		}
		if (answer2.equalsIgnoreCase("yes")) {
			System.out.println(regionAnswer + " Match accepted.");
			System.out.println("Good luck, have fun!");
		} else {
			System.out.println("Match declined.");
			System.out.println("You have been removed from the matchmaking queue.");
		}
		scan.close();
	}
}