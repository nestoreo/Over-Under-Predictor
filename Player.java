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

    // Access a age value.
    public int getAge() {
    	return Integer.valueOf(playerStats[2]);
    }

    // Access a games played value.
    public int getGP() {
    	return Integer.valueOf(playerStats[4]);
    }

    // Access a games started value.
    public int getGS() {
    	return Integer.valueOf(playerStats[5]);
    }

    // Access a minutes per game value.
    public double getMPG() {
    	return Double.valueOf(playerStats[6]);
    }

    // Access a effective field goal percentage value.
    public double getEFG() {
    	return Double.valueOf(playerStats[7]);
    }

    // Access a assists per game value.
    public double getAPG() {
    	return Double.valueOf(playerStats[8]);
    }

    // Access a points per game value.
    public double getPPG() {
    	return Double.valueOf(playerStats[9]);
    }

    // Access a player efficiency rating value.
    public double getPER() {
    	return Double.valueOf(playerStats[10]);
    }

    // Access a true shooting value.
    public double getTS() {
    	return Double.valueOf(playerStats[11]);
    }

    // Access a usage rate value.
    public double getUG() {
    	return Double.valueOf(playerStats[12]);
    }

    // Access a offensive plus/minus value.
    public double getOPM() {
    	return Double.valueOf(playerStats[13]);
    }

    // Access a defensive plus/minus value.
    public double getDPM() {
    	return Double.valueOf(playerStats[14]);
    }

}