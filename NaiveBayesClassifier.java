import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;



public class NaiveBayesClassifier {
	
	//Update the file name here.
	public static String fileName = "data0.txt";
	public static int numOfSample = 10000;
	
	public static void main(String[]args) throws IOException
	{
		long startTime = System.currentTimeMillis();
		
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
        getClassValue(classlist,table,numOfCol);
   
        //Get ride of duplicate element in class array-list.
        getRidOfDuplicate(classlist);
  
        //Calculating the class probability
        getClassProbability(classlist,classProb,table,numOfCol);
     
        //Get the result.
        getResults(classlist, classProb,element, numOfAttribute, numOfCol,table);
        
		long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("The running time is: "+(double)totalTime/1000 +" seconds.");
        
    }
	
    public static void createTable(String[][]table, int row) throws IOException
    {
        BufferedReader in  = new BufferedReader(new FileReader(fileName));
        String line = " ";
        while((line = in.readLine()) != null)
        {
        	String [] values = line.split(" ");
        	
        	for(int i = 0;i<values.length;i++)
        	{
        		table[row][i] = values[i];
        	}
        	  row = row + 1;
        }
        in.close();	
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getRidOfDuplicate(ArrayList classlist)
    {
    	
    	HashSet<Integer> hs = new HashSet<Integer>();
        hs.addAll(classlist);
        classlist.clear();
        classlist.addAll(hs);
    }
    
    public static void getClassValue(ArrayList<Integer>classlist,String[][]table, int numOfCol)
    {
        for(int i = 0;i<numOfSample;i++)
        {
        	classlist.add(Integer.parseInt(table[i][numOfCol-1]));
        }  
    }
    
	
	
		
	public static int getNumOfAttribute() throws IOException
	{
		int counter = 0;	
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line;
		if ((line = in.readLine()) != null)
		{
			String[]values = line.split(" ");
			for(int i = 0;i<values.length;i++)
			{
				counter++;
			}
		}
		in.close();
		return counter-1;
	}
	
	public static void getClassProbability(ArrayList<Integer>classlist,ArrayList<Double>classProb,
			                               String[][]table, int numOfCol)
	{
		   for(int i = 0;i<classlist.size();i++)
	        {
	        	int counter = 0;
	        	double prob = 0.0;
	        	for(int j = 0;j<numOfSample;j++)
	        	{
	        		if(classlist.get(i) == Integer.parseInt(table[j][numOfCol-1]))
	        		{
	        			counter++; 			
	        		}
	        	}
	        	
	        	prob = ((double)(counter-1))/(numOfSample-1);
	        	classProb.add(prob);
	        }
	}
	
	public static void getResults(ArrayList<Integer>classlist, ArrayList<Double>classProb,
			                      ArrayList<String>element, int numOfAttribute, int numOfCol,
			                      String[][]table){
        
		//ArrayList to store the result data.
		ArrayList<String>data = new ArrayList<String>();	
		
		for(int j = 0;j<numOfAttribute;j++){  

	              element.clear();
	            
	              for(int i = 0;i<numOfSample;i++){	
	        	        element.add(table[i][j]);
	              }
	              
	              getRidOfDuplicate(element);
	              
	          for(int k = 0;k<element.size();k++){
	        	  for(int u = 0;u<classlist.size();u++){
	        	      int counter = 0;
	        	      for(int x = 0;x<numOfSample;x++){ 
	        		       if(table[x][j].equals(element.get(k)) && 
	        				   Integer.parseInt(table[x][numOfCol-1]) == classlist.get(u)){
	                           counter++; 		    	   
     		       } 
	        	      }
	        	          data.add("P"+"("+element.get(k)+"|"+classlist.get(u)+")"+ " = "
	        			                    +((((double)counter-1)/(numOfSample-1)))/(classProb.get(u)) );
	               }
	          }
	      }
		leaveOneOutCrossValidation(data,table,classlist,classProb);
	}
	
	public static void leaveOneOutCrossValidation(ArrayList<String>data,String[][]table,
			                                      ArrayList<Integer>classlist,
			                                      ArrayList<Double>classProb)
	{
		
	   ArrayList<Double>p = new ArrayList<Double>();	
		
	   int counter = 0;	
		
       for(int a = 0;a<table.length;a++){
    	for(int c = 0;c<classlist.size();c++){
    	   double probability = classProb.get(c);
    	   for(int b = 0;b<table[0].length-1;b++){
    		   for(int i = 0;i<data.size();i++)
    		   {
                     if( (table[a][b].equals(data.get(i).substring(2, 3))) && 
                     (Integer.parseInt(data.get(i).substring(4, 5)) == classlist.get(c))){
                    			   probability = probability*
                    					   (Double.parseDouble(data.get(i).substring(9)));
                     }
    		   }
    	   }
    	   p.add(probability);   	   
    	}
    	
    	double temp = p.get(0);
    	int Class = 0;
    	
    	//find the maximum probability when with different class
        for(int i = 0;i<p.size();i++)
        {
             if(p.get(i)>=temp)
             {
            	 temp = p.get(i);
             }
    	}
        //find the index of maximum probability.
        //In this case the index is the class itself.
        for(int i = 0;i<p.size();i++)
        {
        	if(temp == p.get(i))
        	{
        		Class = i;
        	}     	
        }
        
        //If the class of testing data matches the class was provided
        //Increase the counter.
        if((Integer.parseInt(table[a][table[0].length-1])) == Class)
        {
        	counter++;
        }
    	p.clear();
       }
       
       double result = (double)counter/(numOfSample-1);
       double performance = (double) (Math.round(result*1000000)/1000000.0); 
       
       System.out.println("The performance of " + fileName + " is: " + performance*100 +"%");
	}
}
