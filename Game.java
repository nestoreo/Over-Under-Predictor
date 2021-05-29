
import java.util.*;
import java.io.*;

public class Game{

	//First team is home team and second is away
	private Team[] teams = new Team[2];

	//average rest days of teams
	private double restDays;

	//starters of game
	private Player[] starters = new Player[10];

	//spread
	private double spread;

	//overunder of game
	private double overUnder;

	//money line
	private double moneyLine;

	//All continuous features
	private ArrayList<Double> allFeatures;

	//feature count
	private int featureCount;

	//If true then over
	private boolean isOver;

	//constructor for the player class
	public Game(String[] game, ArrayList<Player> allPlayers, ArrayList<Team> allTeams)
	{
		//Home team
		teams[0] = getTeam(game[1], allTeams);
		//Away Team
		teams[1] = getTeam(game[0], allTeams);
		//Rest Day Average
		restDays = restDaysAverage(game[2],game[3]);
		//starters first five on home team second five away team
		starters = getPlayers(game, allPlayers);
		//spread
		spread = Math.abs(Double.valueOf(game[14]));
		//Over/Under
		overUnder = Double.valueOf(game[16]);
		//Moenyline
		moneyLine = Math.abs(Double.valueOf(game[18]));

		//total combined points and setting over under to true or not
		double totalScore=Double.valueOf(game[20])+Double.valueOf(game[21]);
		if (totalScore>overUnder)
		{
			isOver = true;
		}
		else
		{
			isOver = false;
		}

		//allfeatures of game (all the values are continuous double values)
		allFeatures = new ArrayList();
		//setting averages for team stats
		for (int i = 0; i<21;i++)
		{
			allFeatures.add((teams[0].getFeature(i+2)+teams[1].getFeature(i+2))/2);
		}

		//setting averages for player stats
		for (int i = 0; i<13;i++)
		{
			if (i!=1)
			{
				double temp = 0;
				for (Player player:starters)
				{
					temp = temp+player.getFeature(i+2);
				}
				//adding average
				allFeatures.add(temp/10);
			}
			
		}
		//adding spread
		allFeatures.add(spread);
		//adding the moneyLine
		allFeatures.add(overUnder);
		//adding the moneyLine
		allFeatures.add(moneyLine);

		//setting featureCount
		featureCount=allFeatures.size();

	}

	//returns the feature value at int feature of allFeatures arrayList
	public int getFeatureCount()
	{
		return featureCount;
	}

	//returns the feature value at int feature of allFeatures arrayList
	public double getTreeFeature(int feature)
	{
		return allFeatures.get(feature);
	}


	//Returns the teams where index 0 is home team and index 1 is away team
	public ArrayList<Double>  getAllFeatures()
	{
		return allFeatures;
	}

	//Returns the teams where index 0 is home team and index 1 is away team
	public Team[] getTeams()
	{
		return teams;
	}
	//Returns the teams where index 0 is home team and index 1 is away team
	public double getRestDays()
	{
		return restDays;
	}

	//returns the starters of game
	public Player[] getStarters()
	{
		return starters;
	}

	//returns the game spread
	public double getSpread()
	{
		return spread;
	}
	
	//returns the over under
	public double getOverUnder()
	{
		return overUnder;
	}

	//returns the moneyLine
	public double getMoneyLine()
	{
		return moneyLine;
	}
	
	//returns whether the game went over or not
	public Boolean getIsOver()
	{
		return isOver;
	}





	//Takes in a team name and list of team objects and returns the correct team object
	private static Player[] getPlayers(String[] game, ArrayList<Player> allPlayers)
	{
		//10 starters of game
		Player[] players = new Player[10];

		//From index 4-13 are the starters
		for (int i=0;i<10;i++)
		{
			//Normalizing string to avoid conflicts
			String playerName = OverUnderClassifier.normalizeName(game[i+4]);
			for (Player player:allPlayers)
			{
				//matching players from box score dataset to player stats dataset
				if (player.getName().contains(playerName)||playerName.contains(player.getName()))
				{
					//adding to correct position to order players
					if(i%2==0)
					{
						players[Integer.valueOf(i/2)] = player;
					}
					else{
						players[Integer.valueOf(i/2+5)] = player;
					}
					//breaking if we found player
					break;
				}
			}
		}

		//Checking which team and returning it
		return players;
	}

	//Takes in a team name and list of team objects and returns the correct team object
	private static Team getTeam(String teamName, ArrayList<Team> allTeams)
	{
		//Normalizing string to avoid conflicts
		teamName = OverUnderClassifier.normalizeName(teamName);

		//Checking which team and returning it
		for (Team team:allTeams)
		{
			if (team.getTeamName().contains(teamName))
			{
				return team;
			}
		}
		return null;
	}

	//turns keys of restdays for two teams to a number and gets average between teams
	private static double restDaysAverage(String firstTeam, String secondTeam)
	{
		//first team rest
		double restFirst;
		//second team rest
		double restSecond;


		if(firstTeam.equals("3+"))//3+ days rest
			restFirst = 3.0;
		else if(firstTeam.equals("2"))//2 days rest
			restFirst = 2.5;
		else if(firstTeam.equals("1"))	//1 day rest
			restFirst = 2.0;
		else if(firstTeam.equals("3IN4"))//3 games in 4 nights 1 day rest
			restFirst = 1.5;
		else if(firstTeam.equals("B2B"))//back to back no days rest
			restFirst = 1.0;
		else//3 games in 4 night back to back no days rest
			restFirst = 0.5;

		//same as above for team 2
		if(secondTeam.equals("3+"))
			restSecond = 3.0;
		else if(secondTeam.equals("2"))
			restSecond = 2.5;
		else if(secondTeam.equals("1"))	
			restSecond = 2.0;
		else if(secondTeam.equals("3IN4"))
			restSecond = 1.5;
		else if(secondTeam.equals("B2B"))
			restSecond = 1.0;
		else
			restSecond = 0.5;

		//returning average
		return (restFirst+restSecond)/2;
	}

	

}