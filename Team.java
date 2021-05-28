import java.util.*;
import java.io.*;

public class Team{

	//number of features of each team
	private final int numStats;

	//team stats
	private final String[] teamStats;


	//constructor for the team class
	public Team(String[] stats)
	{
		//num features
		numStats = stats.length-2;
		//stats
		teamStats = stats;
	}

    // Access feature at i index. Must be between 2-22
    public double getFeature(int i) {
        return Double.valueOf(teamStats[i]);
    }

	// Access a ID value.
    public int getID() {
    	return Integer.valueOf(teamStats[0]);
    }

    // Access a name value.
    public String getTeamName() {
    	return teamStats[1];
    }

}