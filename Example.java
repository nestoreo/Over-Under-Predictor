// A simple container for a set of boolean feature values.

public class Example {

    private final int numFeatures; //the number of features 
    private boolean[] values; // the values of each feature in this example   
    private boolean label; //set to true for positive, false for negatives


    // Constructor
    //Makes room for size number of feature values.
    public Example(int size) {
    	numFeatures = size;
    	values = new boolean[size];
    }
    
    // set the label of this example 
    public void setLabel(boolean l){
    	label = l;
    }

    // Set a feature value.
    public void setFeatureValue(int feature, boolean value) {
    	values[feature] = value;
    }

    // Access a feature value.
    public boolean getFeatureValue(int feature) {
    	return values[feature];
    }
    
    //access the label of this example
    public boolean getLabel(){
    	return label;
    }
    
    //returns a string representation of this example
    public String toString(){
    	String s = "Label:" + label + " feature values: ";
    	for( int i=0; i<values.length; i++)
    		s = s + values[i] + " ";
    	return s;
    }
    
    // returns the number of features of this example
    public int getNumFeatures(){
    	return numFeatures;
    }
}