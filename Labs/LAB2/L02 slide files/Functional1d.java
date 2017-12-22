package L02_Slides;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Functional1d {
	
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
		initTheArray(txcArray);
		txcArray.stream()
			.filter(txc -> txc.getPopulation() > 50_000)
			.filter(txc -> txc.getCounty().equals("Tarrant"))
			.sorted((txc1, txc2) -> txc1.getPopulation()-txc2.getPopulation())
			.forEach(txc -> System.out.println(txc.getName()+" is a large Texas city in "+txc.getCounty()+" county."));
		} 
}