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
		numStats = stats.length;
		//stats
		teamStats = stats;
	}

	// Access a ID value.
    public int getID() {
    	return Integer.valueOf(teamStats[0]);
    }

    // Access a name value.
    public String getTeamName() {
    	return teamStats[1];
    }

    // Access average age value.
    public double getAge() {
    	return Double.valueOf(teamStats[2]);
    }

    // Access wins value.
    public int getWins() {
    	return Integer.valueOf(teamStats[3]);
    }

    // Access losses value.
    public int getLosses() {
    	return Integer.valueOf(teamStats[4]);
    }

    // Access margin of victory value.
    public double getMOV() {
    	return Double.valueOf(teamStats[5]);
    }

    // Access strength of schedule value.
    public double getSOS() {
    	return Double.valueOf(teamStats[6]);
    }

    // Access simple rating system value.
    public double getSRS() {
    	return Double.valueOf(teamStats[7]);
    }

    // Access offensive rating value.
    public double getOR() {
    	return Double.valueOf(teamStats[8]);
    }

    // Access defensive rating value.
    public double getDR() {
    	return Double.valueOf(teamStats[9]);
    }

    // Access net rating value.
    public double getNR() {
    	return Double.valueOf(teamStats[10]);
    }

    // Access pace value.
    public double getPace() {
    	return Double.valueOf(teamStats[11]);
    }

    // Access free throw attempt rate value.
    public double getFTAR() {
    	return Double.valueOf(teamStats[12]);
    }

    // Access 3 point attempt rate value.
    public double get3PAR() {
    	return Double.valueOf(teamStats[13]);
    }

    // Access true shooting value.
    public double getTS() {
        return Double.valueOf(teamStats[14]);
    }

    // Access effective field goal percentage value.
    public double getEFG() {
        return Double.valueOf(teamStats[15]);
    }

    // Access turnover rate value.
    public double getTOV() {
        return Double.valueOf(teamStats[16]);
    }

    // Access Offensive rebound rate value.
    public double getORB() {
        return Double.valueOf(teamStats[17]);
    }

    // Access free throw per field goal attempt value.
    public double getFTFGA() {
        return Double.valueOf(teamStats[18]);
    }

    // Access opponent EFG value.
    public double getOEFG() {
        return Double.valueOf(teamStats[19]);
    }

    // Access opponent tov value.
    public double getOTOV() {
        return Double.valueOf(teamStats[20]);
    }

    // Access defensive rebound rate value.
    public double getDRB() {
        return Double.valueOf(teamStats[21]);
    }

    // Access Opponent ftfga value.
    public double getOFTFGA() {
        return Double.valueOf(teamStats[22]);
    }


}