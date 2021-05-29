//stores all stats of a team
import java.util.*;
import java.io.*;

public class Team{
	//team stats in strings
	private final String[] teamStats;

	//constructor for the team class
	public Team(String[] stats)
	{
		//stats
		teamStats = stats;
	}
    // Access feature at i index. Must be between 2-21 all are doubles
    // 2-Team wins , 3-team losses, 4-team margin of victory
    // 5-team strength of schedule , 6-team simple rating system, 7-team offensive rating
    // 8-team defensive rating, 9-team net rating, 10-team pace,
    // 11-team free throw rate, 12-team three point attempt rate, 13-team true shooting
    // 14-team effective field goal percentage, 15-team turnover rater, 16-team offensive rebound rate
    // 17-team free throw/field goal attempt, 18-opponent effectuve field goal percentage, 19-opponent turnover rate,
    // 20-opponent defensive rebound rate, 21-opponent freet throw per field goal attempt
    //
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