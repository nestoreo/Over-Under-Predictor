//Player class stores all the player stats

import java.util.*;
import java.io.*;

public class Player{

	//player stats in strings
	private final String[] playerStats;

	//constructor for the player class
	public Player(String[] stats)
	{
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

    // Access feature at index i must be between 2-14 all are doubles
    // Indexes 
    // 2-Age, 3-team played for, 4-games played, 5-games started
    // 6-minutes played per game, 7-effective field goal percentage
    // 8-assists per game, 9-points per game, 10-player efficiency rating
    // 11-true shooting percentage, 12 usage rate
    // 13-offensive plus minus, 14-defensive plus-minus
    //
    public double getFeature(int i) {
    	return Double.valueOf(playerStats[i]);
    }

}