import java.util.ArrayList;


public class NaiveBayesClassifier {
	
	//Update the file name here.
	public static String fileName = "";
	public static int numOfSample = 10000;
	
	public static void main(String[]args) throws IOException
	{
		
        int numOfAttribute = getNumOfAttribute();
        
        //ArrayList to store class information.
        ArrayList<Integer> classlist = new ArrayList<Integer>();
        //ArrayList to store class probability.
        ArrayList<Double>  classProb = new ArrayList<Double>();
        //ArrayList to store elements in attribute.
        ArrayList<String>  element = new ArrayList<String>();
        
        
        int numOfCol = getNumOfAttribute()+1;
        String[][]table = new String[numOfSample][numOfCol];
        
        //Read the data from TXT file to 2D-array.
        createTable();
       
        //Get the class value
        getClassValue();
   
        //Get ride of duplicate element in class array-list.
        getRidOfDuplicate();
  
        //Calculating the class probability
        getClassProbability();
     
        //Get the result.
        getResults();
        
        }
        
    }
