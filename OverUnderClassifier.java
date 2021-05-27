//OverUnderClassifier.java
//Trains and tests decision tree classifier

public class OverUnderClassifier {

	//Random number generator
	static Random ran = new Random();

	//Constants
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

		//
		ArrayList<Game> trainOver = new ArrayList();
		ArrayList<Game> trainUnder = new ArrayList();
		ArrayList<Game> trainOver = loadOvers("2018-2019-Box-Scores-train");
		ArrayList<Game> trainUnder = loadUnder("2018-2019-Box-Scores-train");


	}

	private static ArrayList<Game> loadOvers(String data){

		Scanner scan = new Scanner(System.in);

		

	}
}