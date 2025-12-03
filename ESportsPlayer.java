package hello;

public class ESportsPlayer {
	private String name;
	private int elo;
	private int kills;
	private int games;
	
	public ESportsPlayer(String name, int elo, int kills, int games) {
		this.name = name;
		this.elo = elo;
		this.kills = kills;
		this.games = games;
	}
	public ESportsPlayer(int elo) { //simple constructor
		this.name = "Player";
		this.elo = elo;
		this.kills = 0;
		this.games = 0;
	}
	public double getAverageKills() {
		if (games == 0) {
			return 0.0;
		} else {
			return (double) kills / games;
		}
	}
	public int getELO() {
		return elo;
	}
	//gets amount of top players
	public static int topPlayers(ESportsPlayer team[], double target) {
		int total = 0;
		for (ESportsPlayer esplayer : team) {
			if (esplayer.getAverageKills() > target) {
				total++;
			}
		}
		return total;
	}
	public static double[] topPlayersAvgKills(ESportsPlayer[] team) {
		double[] avgKills = new double[team.length];
		for (int i = 0; i < team.length; i++) {
			avgKills[i] = team[i].getAverageKills();
		}
		return avgKills;
	}
	public String getName() {
		return this.name;
	}
	public String toString() {
		return name + "(" + elo + ")" + ": " + kills + " kills over " 
				+ games + " games (" 
				+ String.format("%.2f", getAverageKills()) 
				+ " avg)";
	}
}