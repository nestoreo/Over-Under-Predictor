//OverUnderClassifier.java
//Trains and tests decision tree classifier
import java.util.*;
import java.io.*;
import java.text.Normalizer;

public class OverUnderClassifier {

	//Random number generator
	static Random random = new Random();

	//Constants
	static ArrayList<Player> allPlayers= new ArrayList();

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
		loadPlayers("2018-2019-Player-Stats.txt");

		ArrayList<Game> trainOver =  loadOvers("2018-2019-Box-Scores-train.txt");
		ArrayList<Game> trainUnder =  loadUnders("2018-2019-Box-Scores-train.txt");


	}
	



	private static void loadPlayers(String dataset) throws FileNotFoundException {

		Scanner scan = new Scanner(new File(dataset));

		//Because the data creates multiple rows if a player played for multiple teams that season
		//we need to keep track of that and make sure we do not add the same player multiple times
		ArrayList<String> duplicates = new ArrayList<String>();

		while (scan.hasNextLine())
		{
			String playerLine = scan.nextLine();
			String[] playerStats = playerLine.split("\t");

			//Checking that we have not added the player already
			if ((!duplicates.contains(playerStats[0])) &&(!playerStats[5].equals("0"))){
				playerStats[1] = normalizeName(playerStats[1]);
				allPlayers.add(new Player(playerStats));
			}

			//Adding the player to duplicate if they will have duplicates
			if (playerStats[3].equals("TOT"))
			{
				duplicates.add(playerStats[0]);
			}
		}
		scan.close();


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
		for (Player s : allPlayers)
		{
			System.out.print(s.getOPM());
		}
		scan.close();
		return null;
	}


	//Cleaning the name to avoid dealing with accents, special characters, etc when mapping
	//from other datasets
	private static String normalizeName(String name){

		name = name.replace(" ","").toLowerCase();
		name = Normalizer.normalize(name, Normalizer.Form.NFD);
		name = name.replaceAll("[^\\p{ASCII}]", "");
		name = name.split("[^a-z0-9]")[0].replace(" ","").replaceAll("\\p{Punct}", "");

		return name;

	}
}