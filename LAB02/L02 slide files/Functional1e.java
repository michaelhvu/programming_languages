package L02_Slides;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Functional1e {
	
	private static void initTheArray (ArrayList<texasCitiesClass> txcArray) throws FileNotFoundException,IOException {
		String [] values;
		String line = "";
		BufferedReader br = new BufferedReader (new FileReader("//DISKSTATION/Family_Folder/John/UTA/2017/Fall/CSE 3302/Labs/L02 Cityname_wo_headers.csv"));
		
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			txcArray.add(new texasCitiesClass(values[0],values[1],Integer.parseInt(values[2])));
		}
		br.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<texasCitiesClass> txcArray = new ArrayList<texasCitiesClass>();
		initTheArray(txcArray);
		List<texasCitiesClass> collected = txcArray.stream()
			.filter(txc -> txc.getPopulation() > 50_000)
			.filter(txc -> txc.getCounty().equals("Tarrant"))
			.sorted((txc1, txc2) -> txc1.getPopulation()-txc2.getPopulation())
			.collect(Collectors.toList());
		collected.stream()
			.forEach(txc -> System.out.println(txc.getName()+" is a large Texas city in "+txc.getCounty()+" county."));
		} 
}