import java.util.*;
import java.io.*;

public class Player{

	//number of features of each player
	private final int numStats;

	//player stats
	private final String[] playerStats;


	//constructor for the player class
	public Player(String[] stats)
	{
		//num features
		numStats = stats.length;
		//stats
		playerStats = stats;
	}

	// Access a ID value.
    public int getID() {
    	return Integer.valueOf(playerStats[0]);
    }

    // Access a name value.
    public String getName() {
    	return playerStats[1];
    }

    // Access feature at index i must be between 2-14
    public double getFeature(int i) {
    	return Double.valueOf(playerStats[i]);
    }

}