
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
		//total combined points
		double totalScore=Double.valueOf(game[20])+Double.valueOf(game[21]);
		if (totalScore>overUnder)
		{
			isOver = true;
		}
		else
		{
			isOver = false;
		}

	}

	public void setAllFeatures()
	{
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
				allFeatures.add(temp/10);
			}
			
		}
		allFeatures.add(spread);
		allFeatures.add(overUnder);
		allFeatures.add(moneyLine);
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
	public Player[] getStarters()
	{
		return starters;
	}

	public double getSpread()
	{
		return spread;
	}
	
	public double getOverUnder()
	{
		return overUnder;
	}

	public double getMoneyLine()
	{
		return moneyLine;
	}
	
	public Boolean getIsOver()
	{
		return isOver;
	}





	//Takes in a team name and list of team objects and returns the correct team object
	private static Player[] getPlayers(String[] game, ArrayList<Player> allPlayers)
	{
		Player[] players = new Player[10];

		//From index 4-13 are the starters
		for (int i=0;i<10;i++)
		{
			
			//Normalizing string to avoid conflicts
			String playerName = OverUnderClassifier.normalizeName(game[i+4]);
			for (Player player:allPlayers)
			{
				if (player.getName().contains(playerName)||playerName.contains(player.getName()))
				{
					if(i%2==0)
					{
						players[Integer.valueOf(i/2)] = player;
					}
					else{
						players[Integer.valueOf(i/2+5)] = player;
					}
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

	private static double restDaysAverage(String firstTeam, String secondTeam)
	{
		double restFirst;
		double restSecond;

		if(firstTeam.equals("3+"))
			restFirst = 3.0;
		else if(firstTeam.equals("2"))
			restFirst = 2.5;
		else if(firstTeam.equals("1"))	
			restFirst = 2.0;
		else if(firstTeam.equals("3IN4"))
			restFirst = 1.5;
		else if(firstTeam.equals("B2B"))
			restFirst = 1.0;
		else
			restFirst = 0.5;


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

		return (restFirst+restSecond)/2;
	}

	

}