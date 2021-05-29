// represents a node of the decision tree

import java.util.*;
public class TreeNode {
	TreeNode parent; //parent of this node
	TreeNode trueChild;//the examples which are true on the splitFeature
	TreeNode falseChild;//the examples which are true on the splitFeature

	ArrayList<Example> pos; // the positive examples at this node
	ArrayList<Example> neg; //the negative examples at this node
	boolean decision;

	private boolean[] featuresUsed; // the features already used prior to this node
	private int splitFeature;// the feature that this node will split examples on
	boolean isLeaf = false; //indicates whether the node is a leaf (set to true during pruning)


	public TreeNode(TreeNode par, ArrayList<Example> p, ArrayList<Example> n, int numFeatures){
		parent = par;
		copyParentsFeaturesUsed(numFeatures);
		pos = p;
		neg = n;
		if(pos.size()==0 || neg.size() ==0)
			isLeaf = true;
		splitFeature = -1;
	}


	// Copies the features used until this node	 
	private void copyParentsFeaturesUsed(int features){
		featuresUsed = new boolean[features];
		if(parent==null){
			for(int i=0; i<featuresUsed.length; i++)
				featuresUsed[i] = false;
		}else{
			for(int i =0; i<featuresUsed.length; i++){
				featuresUsed[i]=parent.featuresUsed[i];
			}
		}
	}

	//returns feature used at i
	public boolean featureUsed(int i){
		return featuresUsed[i];
	}

	//returns the splitFeature at f
	public void setSplitFeature(int f){
		splitFeature = f;
		featuresUsed[f] = true;
	}

	// returns the feature this node is split with
	public int getSplitFeature(){
		return splitFeature;
	}

	public String toString(){
		return splitFeature + " \t " + pos.size() + " \t " + neg.size() + "\t " + parent.splitFeature;
	}
}
