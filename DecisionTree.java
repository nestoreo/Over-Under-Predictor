import java.util.ArrayList;


public class DecisionTree {
	private TreeNode root = null; //stores the root of the decision tree


	public void train(ArrayList<Example> examples){
		int numFeatures = 0;
		if(examples.size()>0) //get the number of featuers in these examples
			numFeatures = examples.get(0).getNumFeatures();

		//initialize empty positive and negative lists
		ArrayList<Example> pos = new ArrayList<Example>();
		ArrayList<Example> neg = new ArrayList<Example>();

		//paritition examples into positive and negative ones
		for(Example e: examples){
			if (e.getLabel())
				pos.add(e);
			else
				neg.add(e);
		}

		//create the root node of the tree
		root = new TreeNode(null, pos, neg, numFeatures);

		//call recursive train()  on the root node
		train(root, numFeatures);
	}

	//trains the tree
	private void train(TreeNode node, int numFeatures){

		//First base case is no examples left at all
		if ((node.pos.size()==0&&node.neg.size()>0) || (node.neg.size()==0&&node.pos.size()>0))
		{
			//If they are all positives then true
			if (node.pos.size()>0)
			{
				node.decision=true;
				node.isLeaf=true;
			}
			//If they are all negatives then false
			else
			{
				node.decision=false;
				node.isLeaf=true;
			}
		}
		//Second base case is that remaining examples are only of one label
		else if (node.pos.size()==0 && node.neg.size()==0)
		{
			//We set the node equal to the majority label of the parent. If there are
			//greater or equal positives than negatives then true.
			if (node.parent.pos.size()>=node.parent.neg.size())
			{
				node.decision=true;
				node.isLeaf=true;
			}
			//More negatives then false
			else{
				node.decision=false;
				node.isLeaf=true;
			}
		}



		//Third base case is there are no more features to split on
		else if (!moreFeatures(node, numFeatures))
		{
			//If there are more or equal positives to negatives for this node then true
			if (node.pos.size()>=node.neg.size())
			{
				node.decision=true;
				node.isLeaf=true;
			}
			//if more negatives then false
			else{
				node.decision=false;
				node.isLeaf=true;
			}
		}
		//Else we find the feature with the highest information gain and split on it
		else {
			//Setting the split feature for this node
			node.setSplitFeature(featureMostInfo(node,numFeatures));

			//creating the true and false childs for this node
			createChildren(node, numFeatures);

			//training them
			train(node.trueChild, numFeatures);
			train(node.falseChild, numFeatures);
		}
	}


	//create the children of the node
	private void createChildren(TreeNode node, int numFeatures){

		//Getting true features for true examples
		ArrayList<Example> trueChildPos = trueFeatures(node.pos,node.getSplitFeature());

		//Getting true features for false examples
		ArrayList<Example> trueChildNeg= trueFeatures(node.neg,node.getSplitFeature());;

		//Getting false feaures for true examples
		ArrayList<Example> falseChildPos= falseFeatures(node.pos,node.getSplitFeature());

		//Getting false features for false examples
		ArrayList<Example> falseChildNeg= falseFeatures(node.neg,node.getSplitFeature());;

		//creating trueChild for node
		node.trueChild = new TreeNode(node, trueChildPos, trueChildNeg, numFeatures);

		//creating falseChild for node
		node.falseChild = new TreeNode(node, falseChildPos, falseChildNeg, numFeatures);
	}


	//returns the remaing entropy at node for feature
	private double getRemainingEntropy(int feature, TreeNode node){
		//getting all remaining examples to search through
		ArrayList<Example> allExamples = new ArrayList();
		for (Example e:node.pos)
		{
			allExamples.add(e);
		}
		for (Example e:node.neg)
		{
			allExamples.add(e);
		}

		//Probability of a yes example given feature is true
		double probabilityTrue=0;
		if (trueFeatures(allExamples,feature).size()!=0)
		{
			probabilityTrue = (double)trueFeatures(node.pos,feature).size()/trueFeatures(allExamples,feature).size();
		}


		//Probability of a yes example given feature is false
		double probabilityFalse = 0;
		if (falseFeatures(allExamples,feature).size()!=0)
		{
			probabilityFalse = (double)falseFeatures(node.pos,feature).size()/falseFeatures(allExamples,feature).size();
		}


		//entropy given feature is true
		double entropyFeatTrue = getEntropy(probabilityTrue);

		//entropy given feature is false
		double entropyFeatFalse = getEntropy(probabilityFalse);

		//probability feature is true
		double probabiltyTrueFeat =  (double)trueFeatures(allExamples,feature).size()/allExamples.size();

		//probability feature is false
		double probabiltyFalseFeat =  (double)falseFeatures(allExamples,feature).size()/allExamples.size();

		//returning remaining entropy
		return probabiltyTrueFeat*entropyFeatTrue+probabiltyFalseFeat*entropyFeatFalse;

	}

	//returns entopy given the probability
	private double getEntropy(double probability){
		//if the probability of yes is 0 or 1, it automatically returns 0 entropy
		if (probability==0||probability==1)
		{
			return 0;
		}

		//else return the entropy
		return -log2(probability)*(probability)-log2(1-probability)*(1-probability);
	}

	//return log 2 of d
	private double log2(double d){
		return Math.log(d)/Math.log(2);
	}

	//classifies example
	public boolean classify(Example e){
		//start at the root
		TreeNode currentNode = root;

		//while our currentNode is not a leaf we keep traversing
		while (!currentNode.isLeaf)
		{
			//if the example is true at the split feature move to true child
			if (e.getFeatureValue(currentNode.getSplitFeature())) {
				currentNode = currentNode.trueChild;
			}
			//else move to false child
			else{
				currentNode = currentNode.falseChild;
			}
		}
		//return the decision when a leaf has been reached
		return currentNode.decision;

	}

	//checks if there are more features to split on at node
	public boolean moreFeatures(TreeNode node, int numFeatures){
		//search through all features
		for (int i=0;i<numFeatures;i++)
		{
			//if any features are not used return true
			if (!node.featureUsed(i))
			{
				return true;
			}
		}
		//no more features left return false
		return false;
	}



  //given a node we find the feature we should split at based on infoGain
	public int featureMostInfo(TreeNode node, int numFeatures){

		//set feature equal to zero
		int feature = 0;

		//highest infogain less than zero in case best feature has infogain of zero
		double highestInfoGain = -1;

		//iterate through unused features to find high infoGain
		for (int i=0; i<numFeatures; i++)
		{
			//check the feature is unused
			if (!node.featureUsed(i))
			{
				//temporary infoGain
				double tempInfoGain = getEntropy((double)node.pos.size()/(node.pos.size()+node.neg.size()))-getRemainingEntropy(i,node);
				//if information gain is higher than previous highest save that feature
				if (tempInfoGain>=highestInfoGain)
				{
					feature = i;
					highestInfoGain = tempInfoGain;
				}
			}
		}
		//return best feature
		return feature;
	}


	//Returns an ArrayList of examples where feature is true in exs
	private ArrayList<Example> trueFeatures(ArrayList<Example> exs, int feature)
	{
		//create list of examples
		ArrayList<Example> examples = new ArrayList();
		//search exs
		for (Example e:exs)
		{
			//if feature is true add to list
			if (e.getFeatureValue(feature))
			{
				examples.add(e);
			}
		}
		//return list
		return examples;
	}

	//Returns an ArrayList of examples where feature is false in ex
	private ArrayList<Example> falseFeatures(ArrayList<Example> exs, int feature)
	{
		//create list of examples
		ArrayList<Example> examples = new ArrayList();

		//search exs
		for (Example e:exs)
		{
			//if feature is false we add to list
			if (!e.getFeatureValue(feature))
			{
				examples.add(e);
			}
		}
		//return list
		return examples;
	}

	public void print(){
		printTree(root, 0);
	}


	private void printTree(TreeNode node, int indent){
		if(node== null)
			return;
		if(node.isLeaf){
			if(node.decision)
				System.out.println("Positive");
			else
				System.out.println("Negative");
		}
		else{
			System.out.println();
			doIndents(indent);
			System.out.print("Feature "+node.getSplitFeature() + " = True:" );
			printTree(node.trueChild, indent+1);
			doIndents(indent);
			System.out.print("Feature "+node.getSplitFeature() + " = False:" );//+  "( " + node.falseChild.pos.size() + ", " + node.falseChild.neg.size() + ")");
			printTree(node.falseChild, indent+1);
		}
	}
	private void doIndents(int indent){
		for(int i=0; i<indent; i++)
			System.out.print("\t");
	}
}
