import java.util.*;
import java.io.*;

public class Player{

	//number of features of each player
	private final int numStats;

	//player id
	private final int id;

	//name of player
	private final String name;

	//age of player
	private final int age;

	//Games played total
	private final int gamesPlayed;

	//Games where player was starter
	private final int gamesStarted;

	//minutes per game of player
	private final double minutesPG;

	//assists of per game
	private final double assistsPG;

	//effective field goal percentage
	private final double eFG;

	//points per game
	private final double pointsPG

	//player efficiency rating
	private final double playerER;

	//true shooting percentage
	private final double trueShooting;

	//usage rate
	private final double usageRate;

	//Offensive Plus/Minus
	private final double offensivePM;

	//Offensive Plus/Minus
	private final double defensivePM;

	public Player(String[] stats)
	{
		numStats = stats.length;
		id= stats[0];


		name = stats[1];

		age = stats[2];
		gamesPlayed = stats[4];
		gamesStarted = stats[5];
		minutesPG = stats[6];
		
		eFG = stats[7];
		assistsPG = stats[8];
		pointsPG = stats[9];
		playerER = stats[10];
		trueShooting = stats[11];
		usageRate = stats[12];
		offensivePM = stats[13];
		defensivePM = stats[14];

	}
}