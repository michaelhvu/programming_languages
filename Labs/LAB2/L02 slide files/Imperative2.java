package L02_Slides;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Imperative2 {
	
	private static void initTheArray (ArrayList<texasCitiesClass> txcArray) throws FileNotFoundException,IOException {
		String [] values;
		String line = "";
		BufferedReader br = new BufferedReader (new FileReader("F:/FamilyFolderBackup/John/UTA/CSE 3302/Cityname.csv"));
		
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			txcArray.add(new texasCitiesClass(values[0],values[1],Integer.parseInt(values[2])));
		}
		br.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<texasCitiesClass> txcArray = new ArrayList<texasCitiesClass>();
		int popsum=0;
		initTheArray(txcArray);
		for (texasCitiesClass txc: txcArray)
			if(txc.getCounty().equals("Tarrant"))
				popsum+=txc.getPopulation();
		System.out.println("The total population of Tarrant County cities is "+popsum);
	}

}
