package L02_Slides;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Imperative1 {
	
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
	
	public static void main(String[] args) throws IOException {
		ArrayList<texasCitiesClass> txcArray = new ArrayList<texasCitiesClass>();
		initTheArray(txcArray);
		for (int i=0;i<txcArray.size();i++)
			if(txcArray.get(i).getPopulation() > 50_000 && txcArray.get(i).getCounty().equals("Tarrant"))
				System.out.println(txcArray.get(i).getName()+" is a large Texas city in "+txcArray.get(i).getCounty()+" county.");
	}
}