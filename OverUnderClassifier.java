//OverUnderClassifier.java
//Trains and tests decision tree classifier
import java.util.*;
import java.io.*;
import java.text.Normalizer;

public class OverUnderClassifier {

	//Random number generator
	static Random random = new Random();

	//Constants
	static ArrayList<Player> allPlayers;

	static int exampleCount;
	static int featureCount;
	static double[] threshold;

	//training examples
	static ArrayList<Game> trainExs;

	//processing data
	public static void main(String[] args) throws FileNotFoundException {


		classifyExamples("2018-2019-Box-Scores.txt");
	}

	private static void classifyExamples(String data) throws FileNotFoundException {

		//Populating stats of all players
		allPlayers = loadPlayers("2018-2019-Player-Stats.txt");

		ArrayList<Game> trainOver =  loadOvers("2018-2019-Box-Scores-train.txt");
		ArrayList<Game> trainUnder =  loadUnders("2018-2019-Box-Scores-train.txt");


	}



	private static ArrayList<Player> loadPlayers(String dataset) throws FileNotFoundException {

		Scanner scan = new Scanner(new File(data));

		//Because the data creates multiple rows if a player played for multiple teams that season
		//we need to keep track of that and make sure we do not add the same player multiple times
		Arraylist<String> duplicates = new ArrayList<String>();

		while (scan.hasNextLine())
		{
			String playerLine = scan.nextLine();
			String[] playerStats = playerLine.split(",");
			
			//cleaning the name to avoid dealing with accents, special characters, etc
			playerStats[1].split("\\")[0].replace(" ","").replaceAll("\\p{Punct}", "");
			playerStats[1] = Normalizer.normalize(playerStats[1], Normalizer.Form.NFD);
			playerStats[1] = playerStats[1].replaceAll("[^\\p{ASCII}]", "");
			playerStats[1] = playerStats[1].toLowerCase();
			System.out.println(playerStats[1]);

			//Checking that we have not added the player already
			if ((!duplicates.contains(playerStats[0])) &&(!playerStats[5].equals("0"))){
				allPlayers.add(new Player(playerStats));
			}

			//Adding the player to duplicate if they will have duplicates
			if (playerStats[3].equals("TOT"))
			{
				duplicates.add(playerStats[0]);
			}
		}

	}


     private static ArrayList<Game> loadOvers(String data) throws FileNotFoundException {

		Scanner scan = new Scanner(new File(data));

		while (scan.hasNextLine())
		{
			String game = scan.nextLine();
			String[] gameSplits = game.split("\t");
			System.out.println(gameSplits);
		}
		scan.close();
		return null;

	}


	private static ArrayList<Game> loadUnders(String data) throws FileNotFoundException {

		Scanner scan = new Scanner(new File(data));

		while (scan.hasNextLine())
		{
			String game = scan.nextLine();
			String[] gameSplits = game.split("\t");
			for (String s : gameSplits)
			{
				System.out.print(s+"|");
			}
			System.out.println("");
			
		}
		scan.close();
		return null;
	}
}