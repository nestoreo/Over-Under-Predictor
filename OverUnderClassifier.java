//OverUnderClassifier.java
//Trains and tests decision tree classifier
import java.util.*;
import java.io.*;
import java.text.Normalizer;

public class OverUnderClassifier {

	//Random number generator
	static Random random = new Random();

	//Constants

	//AllPlayers that started game
	static ArrayList<Player> allPlayers;
	//All teams
	static ArrayList<Team> allTeams;
	//number of training examples
	static int exampleCount;
	//number of features
	static int featureCount;

	//training examples
	static ArrayList<Game> trainExs;


	//processing data
	public static void main(String[] args) throws FileNotFoundException {

		classifyExamples();
	}

	private static void classifyExamples() throws FileNotFoundException {

		//Populating stats of all players
		allPlayers = loadPlayers("2018-2019-Player-Stats.txt");
		
		//Populating stats of all teams
		allTeams = loadTeams("2018-2019-Team-Stats.txt");
		
		//Populating all games in the training set
		ArrayList<Game> trainingGames = loadGames("2018-2019-Box-Scores-train.txt");

		//Updating example count
		exampleCount = trainingGames.size();

		for (Game game:trainingGames)
		{
			game.setAllFeatures();
			System.out.println(game.getAllFeatures());
		}



	}







	
	private static ArrayList<Game> loadGames(String data) throws FileNotFoundException {

		Scanner scan = new Scanner(new File(data));

		ArrayList<Game> allGames = new ArrayList();


		while (scan.hasNextLine())
		{
			String line1 = scan.nextLine();
			String line2 = scan.nextLine();

			String[] gameSplit1 = line1.split("\t");
			String[] gameSplit2 = line2.split("\t");
			
			String[] game = new String[gameSplit1.length*2];
			for (int i=0;i<gameSplit1.length;i++)
			{
				
				game[2*i]=gameSplit1[i];
				game[2*i+1]=gameSplit2[i];
			}

			//Making sure that the overUnder did not equal the final score
			double totalScore=Double.valueOf(game[20])+Double.valueOf(game[21]);
			double overUnder = Double.valueOf(game[16]);
			if (totalScore!=overUnder)
			{
				allGames.add(new Game(game, allPlayers, allTeams));
			}

			
			
		}

		for (Game g: allGames)
		{
			System.out.println("Home: "+g.getTeams()[0].getTeamName()+" vs. Away: "+g.getTeams()[1].getTeamName());
			System.out.println("Rest Days Average: "+g.getRestDays());
			System.out.println("Moneyline: "+g.getMoneyLine());
			System.out.println("isOver: "+g.getIsOver());
			System.out.println("spread: "+g.getSpread());
			System.out.println("OverUnder: "+g.getOverUnder());

			for (Player player:g.getStarters())
			{
				System.out.println("Starter: "+player.getName());
			}

		}
		scan.close();
		return allGames;
	}

	//reads the dataset with statistics of all teams and creates a team object for each
	//and adds it to the allTeams arraylist
	private static ArrayList<Team> loadTeams(String dataset) throws FileNotFoundException {

		//needed to read dataset
		Scanner scan = new Scanner(new File(dataset));

		ArrayList<Team> teams = new ArrayList<Team>();

		while (scan.hasNextLine())
		{
			//Getting team stat line
			String teamLine = scan.nextLine();
			//Getting each individual stat
			String[] teamStats = teamLine.split("\t");
			//normalizing team name
			teamStats[1] = normalizeName(teamStats[1]);
			//Adding team to allTeams
			teams.add(new Team(teamStats));
		}
		scan.close();

		return teams;
	}




	//reads the dataset with statistics of all players of that season and creates a player object for each
	//and adds it to the allPlayers arraylist
	private static ArrayList<Player> loadPlayers(String dataset) throws FileNotFoundException {

		//needed to read dataset
		Scanner scan = new Scanner(new File(dataset));

		//Because the data creates multiple rows if a player played for multiple teams that season
		//we need to keep track of that and make sure we do not add the same player multiple times
		ArrayList<String> duplicates = new ArrayList<String>();

		ArrayList<Player> players = new ArrayList<Player>();

		while (scan.hasNextLine())
		{
			//Getting player stat line
			String playerLine = scan.nextLine();
			//Getting each individual stat
			String[] playerStats = playerLine.split("\t");

			//Checking that we have not added the player already and that they did start a game
			if ((!duplicates.contains(playerStats[0])) &&(!playerStats[5].equals("0"))){
				//normalizing name
				playerStats[1] = normalizeName(playerStats[1]);
				//Adding player to starters
				players.add(new Player(playerStats));
			}

			//Adding the player to duplicate if they will have duplicates
			if (playerStats[3].equals("TOT"))
			{
				duplicates.add(playerStats[0]);
			}
		}
		scan.close();

		return players;
	}





	//Cleaning the name to avoid dealing with accents, special characters, etc when mapping
	//from other datasets -- normalizeName(Álex Abrines\abrinal01) -> alexabrines
	public static String normalizeName(String name){

		//Removing white space and making lower case
		name = name.replace(" ","").toLowerCase();
		//Needed to replace accented characters with normal characters
		name = Normalizer.normalize(name, Normalizer.Form.NFD);
		//Replacing special character and removing the player id
		name = name.replaceAll("[^\\p{ASCII}]", "").replace("\\", "\t").split("\t")[0].replaceAll("\\p{Punct}", "");

		return name;

	}
}