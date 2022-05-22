//OverUnderClassifier.java
//Trains and tests decision tree classifier
import java.util.*;
import java.io.*;
import java.text.Normalizer;

public class OverUnderClassifier {

	//CONSTANTS

	//AllPlayers that started game
	static ArrayList<Player> allPlayers;
	//All teams
	static ArrayList<Team> allTeams;
	//AllPlayers that started game
	static ArrayList<Player> allPlayersNew;
	//All teams
	static ArrayList<Team> allTeamsNew;
	//number of training examples
	static int exampleCount;
	//number of training examples
	static int overExampleCount;
	//number of training examples
	static int underExampleCount;
	//number of features
	static int featureCount;
	//Entropy of the training examples
	static double exampleEntropy;
	//training examples
	static ArrayList<Example> trainExamples;
	//the split points of the data at each feature
	static double[] splitPoints;




	//Training data and running
	public static void main(String[] args) throws FileNotFoundException {
		//Populating stats of all players
		allPlayers = loadPlayers("2018-2019-Player-Stats.txt");
		
		//Populating stats of all teams
		allTeams = loadTeams("2018-2019-Team-Stats.txt");

		
		//classifying data
		classifyExamples();
	}

	//Trains data and runs testing examples
	private static void classifyExamples() throws FileNotFoundException {

		//Populating all games in the training set (912 games in training set some are thrown out
		//in loadGames because they did not go over or under but were exact)
		ArrayList<Game> trainingGames = loadGames("2018-2019-Box-Scores-train.txt", allPlayers, allTeams);

		//Updating example count
		exampleCount = trainingGames.size();
		

		//updating overCount
		overExampleCount=0;
		//updating underCount
		underExampleCount =0;

		//setting counts
		for (Game game:trainingGames)
		{
			//updating counts
			if (game.getIsOver())
			{
				overExampleCount++;
			}
			else{
				underExampleCount++;
			}
			
		}

		//feature count
		featureCount = trainingGames.get(0).getFeatureCount();

		//updating entropy of training set
		exampleEntropy = getEntropy((double)overExampleCount/(exampleCount));

		//setting splitPoints based on infoGain
		splitPoints = new double[featureCount];
		for (int i=0; i<featureCount;i++)
		{
			//saves the best split point to categorize the data
			splitPoints[i] = getBestSplitPoint(i,trainingGames);
		}


		//Creates the examples from the trainingGames
		trainExamples = loadExamples(trainingGames);
		
		//creating decision tree
		DecisionTree tree = new DecisionTree();

		//training with our examples
		tree.train(trainExamples);

		//printing tree
		//tree.print();

		//loading the games
		ArrayList<Game> testGames = loadGames("2018-2019-Box-Scores-test.txt", allPlayers, allTeams);

		//counts for test examples
		int overTestCount =0;
		int underTestCount=0;
		//figuring out their counts
		for (Game game:testGames)
		{
			//updating counts
			if (game.getIsOver())
			{
				overTestCount++;
			}
			else{
				underTestCount++;
			}
			
		}
		//loading the test examples from test games
		ArrayList<Example> testExamples = loadExamples(testGames);

		//checking the accuracy of each
		int correctOver = 0;
		int correctUnder = 0;
		int incorrect = 0;
		//classifying examples and checking is correct
		for (Example ex: testExamples)
		{
			//if correct classifiction increment counts
			if (tree.classify(ex))
			{
				if (ex.getLabel())
					correctOver++;
				else
					incorrect ++;
			}
			else {
				if (!ex.getLabel())
					correctUnder++;
				else
					incorrect ++;
			}
		}
		//printing accuracy stats
		System.out.println("Accuracy of total test examples: "+((double)correctOver+correctUnder)/(overTestCount+underTestCount));
		System.out.println("Accuracy of over test examples: "+((double)correctOver)/overTestCount);
		System.out.println("Accuracy of under test examples: "+((double)correctUnder)/underTestCount);
		System.out.println("Total incorrect examples: "+ incorrect);

		//Populating stats of all players
		allPlayersNew = loadPlayers("2021-2022-Player-Stats.txt");
		
		//Populating stats of all teams
		allTeamsNew = loadTeams("2021-2022-Team-Stats.txt");

		ArrayList<Game> testGame2 = loadGames("testgames.txt", allPlayersNew, allTeamsNew);
		ArrayList<Example> testExample2 = loadExamples(testGame2);
		String outcome = "over";
		if (!tree.classify(testExample2.get(1)))
		{
			outcome = "under";
		}
		System.out.println("The Over/Under Classification of this game is : " + outcome);


//counts for test examples
		 overTestCount =0;
		 underTestCount=0;
		//figuring out their counts
		for (Game game:testGame2)
		{
			//updating counts
			if (game.getIsOver())
			{
				overTestCount++;
			}
			else{
				underTestCount++;
			}
			
		}

		//checking the accuracy of each
		 correctOver = 0;
		 correctUnder = 0;
		 incorrect = 0;
		//classifying examples and checking is correct
		for (Example ex: testExample2)
		{
			//if correct classifiction increment counts
			if (tree.classify(ex))
			{
				if (ex.getLabel())
					correctOver++;
				else
					incorrect ++;
			}
			else {
				if (!ex.getLabel())
					correctUnder++;
				else
					incorrect ++;
			}
		}
		//printing accuracy stats
		System.out.println("Accuracy of total test examples: "+((double)correctOver+correctUnder)/(overTestCount+underTestCount));
		System.out.println("Accuracy of over test examples: "+((double)correctOver)/overTestCount);
		System.out.println("Accuracy of under test examples: "+((double)correctUnder)/underTestCount);
		System.out.println("Total incorrect examples: "+ incorrect);



		// while(true){

		// 	//used  to look through data
		// 	Scanner scan = new Scanner(System.in);
		// 	//allGames we will return

		// 	//check if there are more games
		// 	System.out.println("Enter Team 1: ");
		// 	//one game has two lines
		// 	String team1 = scan.nextLine();

		// 	//check if there are more games
		// 	System.out.println("Enter Team 2: ");
		// 	//one game has two lines
		// 	String team2 = scan.nextLine();

		// 	System.out.println("Enter Team 1 Rest (3+, 2, 1, 3IN4, B2B, 3IN4-B2B): ");
		// 	String t1rest = scan.nextLine();

		// 	System.out.println("Enter Team 2 Rest (3+, 2, 1, 3IN4, B2B, 3IN4-B2B): ");
		// 	String t2rest = scan.nextLine();

		// 	System.out.println("Enter Team 1 Starters (comma separated): ");
		// 	String t1starters = scan.nextLine();

		// 	System.out.println("Enter Team 2 Starters (comma separated): ");
		// 	String t2starters = scan.nextLine();


		// 	System.out.println("Spread :");
		// 	String t1spread = scan.nextLine();

		// 	System.out.println("Over/Under");
		// 	String gOverUnder = scan.nextLine();

		// 	System.out.println("Money Line for Team 1: ");
		// 	String gmoneyline1 = scan.nextLine();

		// 	System.out.println("Money Line for Team 2: ");
		// 	String gmoneyline2 = scan.nextLine();

		// 	scan.close();

		// 	String[] game = new String[22];

		// 	game[0] = team1;
		// 	game[1] = team2;
		// 	game[2] = t1rest;
		// 	game[3] = t2rest;
		// 	game = addToArray(game, t1starters.split(","), 4); 
		// 	game = addToArray(game, t2starters.split(","), 9); 
		// 	game[14] = t1spread;
		// 	game[15] = t1spread;
		// 	game[16] = gOverUnder;
		// 	game[17] = gOverUnder;
		// 	game[18] = gmoneyline1;
		// 	game[19] = gmoneyline2;
		// 	game[20] = "0";
		// 	game[21] = "0";

		// 	//System.out.println(game[0]+" "+game[1]+" "+game[2]+" "+game[3]+" "+game[4]+" "+game[5]+" "+game[6]+" "+game[7]+" "+game[8]+" "+game[9]+" "+game[10]+" "+game[11]+" "+game[12]+" "+game[13]+" "+game[14]+" "+game[15]+" "+game[16]+" "+game[17]+" "+game[18]+" "+game[19]+" "+ game[20]+" "+game[21]);
		// 	ArrayList<Game> testGame = new ArrayList();
		// 	testGame.add(new Game(game, allPlayersNew, allTeamsNew));

		// 	ArrayList<Example> testExample = loadExamples(testGames);

		// 	String outcome = "over";
		// 	if (!tree.classify(testExample.get(0)))
		// 	{
		// 		outcome = "under";
		// 	}
		// 	System.out.println("The Over/Under Classification of this game is : " + outcome);

		// }
	}

	private static String[] addToArray( String[] array1, String[] array2, int index)
	{
		for (int i =index; i < index+array2.length; i++)
		{
			array1[i] = array2[i-index];
		}

		return array1;
	}

	//Creating examples list from games
	private static ArrayList<Example> loadExamples(ArrayList<Game> games)
	{
		//returning the correct examples
		ArrayList<Example> examples = new ArrayList<Example>();

		//creating each game to an examples
		for (int i=0;i<games.size();i++)
		{
			//adding it and setting feature count
			examples.add(new Example(featureCount));
			//setting label
			examples.get(i).setLabel(games.get(i).getIsOver());
			//setting each individual feature
			for(int j =0;j<featureCount;j++)
			{

				//true if value is greater than splitPoint
				if (games.get(i).getTreeFeature(j)>splitPoints[j])
					examples.get(i).setFeatureValue(j,true);
				else//false if less than (SplitPoints can never equal a value because they are midpoints)
					examples.get(i).setFeatureValue(j,false);
			}
		}
		return examples;
	}

	//returns the best splitPoint given games and which feature
	private static double getBestSplitPoint(int feature, ArrayList<Game> games)
	{
		//size of games
		int size = games.size();
		//selection sort in order to sort games by that exact feature in order to find midpoints effectively
		for(int i = 0; i <size;i++)
		{
			for (int j=i+1;j<size;j++)
			{
				if (games.get(i).getTreeFeature(feature)>games.get(j).getTreeFeature(feature))
				{
					Game temp = games.get(i);
					games.set(i,games.get(j));
					games.set(j, temp);
				}
			}
		}

		//finding the best split point
		//highest info gain
		double highestInfoGain = 0;
		//best split
		double bestSplit=0;
		//Going through all games and checking the info gain if we split features at that midpoint
		for (int i = 0; i<size-1;i++)
		{
			//checking next feature does not equal this feature value
			if (games.get(i).getTreeFeature(feature)!=games.get(i+1).getTreeFeature(feature))
			{
				//midpoint
				double tempSplit = (games.get(i).getTreeFeature(feature)+games.get(i+1).getTreeFeature(feature))/2;
				//checking if infoGain is better than previous best
				if (getInfoGain(feature,tempSplit,games)>highestInfoGain)
				{
					//if so save that gain and the split
					bestSplit = tempSplit;
					highestInfoGain = getInfoGain(feature, bestSplit, games);
					
				}
			}
		}
		//returning best split at feature
		return bestSplit;
	}

	//returns information gain
	private static double getInfoGain(int feature, double splitPoint, ArrayList<Game> games){

		return exampleEntropy - getRemainingEntropy(feature,splitPoint, games);
	}

	//returns the remaining entropy based on the feature split point and the games
	private static double getRemainingEntropy(int feature, double splitPoint, ArrayList<Game> games){

		//games where feature is over split point
		ArrayList<Game> trueFeatures = new ArrayList<Game>();
		//games where feature is under split point
		ArrayList<Game> falseFeatures = new ArrayList<Game>();

		//Count of overs when feature is true (or over split point)
		int overTrueFeat = 0;
		//count of overs when feature is false (or under split point)
		int overFalseFeat = 0;

		//setting up the variables above
		for (Game game:games)
		{
			//checking where feature is true
			if (game.getTreeFeature(feature)>splitPoint)
			{
				trueFeatures.add(game);
				//checking if feature is over
				if (game.getIsOver())
				{
					overTrueFeat++;
				}
			}
			//checking wwhether feature is true
			else{
				falseFeatures.add(game);
				//checking if feature is over
				if(game.getIsOver())
				{
					overFalseFeat++;
				}
			}
		}

		//probability of being greater than split point at feature
		double trueProbability = trueFeatures.size()/exampleCount;

		//probability of being less than the split point at feature
		double falseProbability = falseFeatures.size()/exampleCount;

		//entropy of examples where feature is true
		double entropyFeatTrue = getEntropy((double)overTrueFeat/trueFeatures.size());

		//entropy where feature is false
		double entropyFeatFalse = getEntropy((double)overFalseFeat/falseFeatures.size());

		return trueProbability*entropyFeatTrue+falseProbability*entropyFeatFalse;

	}

	//returns log_2 of d
	private static double log2(double d){
		return Math.log(d)/Math.log(2);
	}

	//reuturns entropy or 0 if probability = 0 or 1
	private static double getEntropy(double probability){
		//if the probability of yes is 0 or 1, it automatically returns 0 entropy
		if (probability==0||probability==1)
		{
			return 0;
		}

		//else return the entropy
		return -log2(probability)*(probability)-log2(1-probability)*(1-probability);
	}

	//returns the games given in the data set
	private static ArrayList<Game> loadGames(String data, ArrayList<Player> players, ArrayList<Team> teams) throws FileNotFoundException {

		//used  to look through data
		Scanner scan = new Scanner(new File(data));
		//allGames we will return
		ArrayList<Game> allGames = new ArrayList();

		//check if there are more games
		while (scan.hasNextLine())
		{
			//one game has two lines
			String line1 = scan.nextLine();
			String line2 = scan.nextLine();

			//splitting each line
			String[] gameSplit1 = line1.split("\t");
			String[] gameSplit2 = line2.split("\t");
			
			//adding the two arrays in a strategic way to make it easier to organize later
			String[] game = new String[gameSplit1.length*2];
			for (int i=0;i<gameSplit1.length;i++)
			{
				
				game[2*i]=gameSplit1[i];
				game[2*i+1]=gameSplit2[i];
			}

			//Making sure that the overUnder did not equal the final score
			double totalScore=Double.valueOf(game[20])+Double.valueOf(game[21]);
			double overUnder = Double.valueOf(game[16]);
			//if it equals we throw it out
			if (totalScore!=overUnder)
			{
				allGames.add(new Game(game, players, teams));
			}			
		}

		scan.close();
		//return games
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