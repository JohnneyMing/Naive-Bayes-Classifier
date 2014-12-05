import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NaiveBayesClassifier {
	
	//Update the file name here.
	public static String fileName = "data0.txt";
	public static int numOfSample = 10000;
	
	public static void main(String[]args) throws IOException
	{
        int row = 0;
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
        createTable(table,row);
       
        //Get the class value
        getClassValue();
   
        //Get ride of duplicate element in class array-list.
        getRidOfDuplicate();
  
        //Calculating the class probability
        getClassProbability();
     
        //Get the result.
        getResults();
        
        }
          public static void createTable(String[][]table, int row) throws IOException {
              BufferedReader in  = new BufferedReader(new FileReader(fileName));
              String line = " ";
              while((line = in.readLine()) != null){
        	String [] values = line.split(" ");
        	for(int i = 0;i<values.length;i++)
        	{
        		table[row][i] = values[i];
        	}
        	  row = row + 1;
        }
        in.close();	
    }
  }
