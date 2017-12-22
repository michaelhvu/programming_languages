package L02_Slides;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Functional2 {
	
	private static void initTheArray (ArrayList<texasCitiesClass> txcArray) throws FileNotFoundException,IOException {
		String [] values;
		String line = "";
		BufferedReader br = new BufferedReader (new FileReader("//DISKSTATION/Family_Folder/John/UTA/CSE 3302/Labs/Cityname.csv"));
		
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			txcArray.add(new texasCitiesClass(values[0],values[1],Integer.parseInt(values[2])));
		}
		br.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<texasCitiesClass> txcArray = new ArrayList<texasCitiesClass>();
		initTheArray(txcArray);
		int pop = txcArray.stream()
			.filter( txc -> txc.getCounty().equals("Tarrant"))
			.map( txc -> txc.getPopulation())
			.reduce(0, Integer::sum);
		System.out.println("Tarrant County population from the list is  "+pop);
	}
}