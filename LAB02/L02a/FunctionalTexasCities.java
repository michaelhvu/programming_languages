package L02a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionalTexasCities {	
		private static void initTheArray (ArrayList<TexasCitiesClass> txcArray, ArrayList<String> counties) throws FileNotFoundException,IOException {
			String [] values;
			String line = "";
			BufferedReader br = new BufferedReader (new FileReader("/Users/michaelvu/eclipse-workspace/CSE 3302 Programming Languages/LAB02/L02a/L02a Cityname_wo_headers.csv"));
			
			while ((line = br.readLine()) != null) {
				values = line.split(",");
				txcArray.add(new TexasCitiesClass(values[0],values[1],Integer.parseInt(values[2])));
				counties.add(values[1]);
			}
			br.close();
		}
	
	/*
	 * i.	The county name will be listed in A-Z alphabetic order
	 * ii.	The number of cities in that county is listed
	 * iii.	The total population of that county is listed
	 * iv.	The average population of that county is listed 
	 * v. 	The name of the largest city is listed
	 * vi.	The population of the largest city is listed
	 * 		County Name | # of Cities | Population of County | Avg Population | Largest City | Largest City Population
	 */
		
		public static void main(String[] args) throws FileNotFoundException, IOException {
	        
			ArrayList<TexasCitiesClass> txcArray = new ArrayList<TexasCitiesClass>();
			ArrayList<String> counties = new ArrayList<String>();
			initTheArray(txcArray, counties);
			
			// Get collection without duplicates & sort in alphabetical order
			ArrayList<String> countyList = (ArrayList<String>) counties.stream().sorted().distinct().collect(Collectors.toList());
			
			ArrayList<Integer> citiesNum = new ArrayList <Integer> ();				  // The number of cities in that county is listed
			ArrayList<Integer> population = new ArrayList <Integer> ();				  // The total population of that county is listed
			ArrayList<Integer> avgpop = new ArrayList <Integer> (); 					  // The average population of that county is listed 
			ArrayList<TexasCitiesClass> maxpop = new ArrayList <TexasCitiesClass> ();  // The name & population of the largest city is listed
			
			countyList.stream()
				.forEach( county ->{
					
					// Get number of cities per county
					citiesNum.add((int)txcArray.stream()
							.filter( txc -> txc.getCounty().equals(county))
							.count());
					
					// Get population of each county
					population.add((int)txcArray.stream()
							.filter( txc -> txc.getCounty().equals(county))
							.mapToInt( txc -> txc.getPopulation())
							.reduce(0, Integer::sum));
							
					// Get average population of each county
					avgpop.add((int) txcArray.stream()
							.filter( txc -> txc.getCounty().equals(county))
							.mapToDouble( txc -> txc.getPopulation())
							.average()
							.getAsDouble());
					
					// Get the name & population of the largest city in each county
					maxpop.add(txcArray.stream()
							.filter( txc -> txc.getCounty().equals(county))
							.max((txc1, txc2) -> Integer.compare(txc1.getPopulation(), txc2.getPopulation()))
							.get());	
				});
			
			// thousands separators
			NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));
			
			BufferedWriter out = new BufferedWriter(new FileWriter("L02a_Functional_Output.txt"));
		    try {
		    		out.write("County Name\t" + "No. Cities\t" + "Total Pop.\t" + "Avg. Pop.\t" + "Largest City\t" + "Population\n");
		    		IntStream.range(0, countyList.size())
		    		.forEach( i -> {
		    			try {
		    					out.write(countyList.get(i) + "\t" + citiesNum.get(i) + "\t" + formatter.format(population.get(i)) + "\t" + formatter.format(avgpop.get(i))+ "\t" + maxpop.get(i).getName() + "\t" + formatter.format(maxpop.get(i).getPopulation()) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
		    		});
		    } catch(IOException e1) {
		        System.out.println("Error during writing");
		    } finally {
		        out.close();
		    }
		} 
}
