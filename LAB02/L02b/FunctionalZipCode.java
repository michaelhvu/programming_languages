/*
 * Michael Vu
 * CSE 3302
 * Programming Languages
 * LAB 02b
 * Due Sunday, October 29, 2017
 */
package L02b;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import L02b.zipCodeClass;

public class FunctionalZipCode {
	private static void initTheArray (ArrayList<zipCodeClass> zccArray, ArrayList<String> counties) throws FileNotFoundException,IOException {
		String [] values;
		String line = "";
		BufferedReader br = new BufferedReader (new FileReader("/Users/michaelvu/eclipse-workspace/CSE 3302 Programming Languages/LAB02/L02b/L02b zip_code_database.csv"));
		
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			zccArray.add(new zipCodeClass(Integer.parseInt(values[0]),values[1],values[2], values[3], Integer.parseInt(values[4])));
			counties.add(values[3]);
		}
		br.close();
	}
	
	private static IntStream getCountyPop(ArrayList<zipCodeClass> zccArray, String countyName) {
		// method returns a stream of integer populations for the given county
		return zccArray.stream()
				.filter(zcc -> zcc.getCountyName().equals(countyName))
				.mapToInt(zcc -> zcc.getEstPop());
	}

	private static IntStream getCityPop(ArrayList<zipCodeClass> zccArray, String countyName, String cityName) {
		// method returns a stream of integer populations for the given city
		return zccArray.stream()
				.filter(zcc -> zcc.getCountyName().equals(countyName))
				.filter(zcc -> zcc.getCityName().equals(cityName))
				.mapToInt(zcc -> zcc.getEstPop());
	}
	
	private static void writeCountyData (ArrayList<zipCodeClass> zccArray, ArrayList<String> countyList) throws IOException {
			FileWriter writer = new FileWriter("/Users/michaelvu/eclipse-workspace/CSE 3302 Programming Languages/LAB02/L02b/Problem_2b_output.txt");
			BufferedWriter out = new BufferedWriter(writer);
			try {
			out.write("County name"
						+"\t"+"County Pop."
						+"\t"+"City Name"
						+"\t"+"City Pop."
						+"\t"+"No. of Zip Codes\n");
			DecimalFormat IntWithComma = new DecimalFormat("###,###,###");
			
			countyList.stream()
			.forEach(county -> {
				zccArray.stream()
					.filter(q -> q.getCountyName().equals(county))
					.sorted((zcc1, zcc2) -> zcc1.getCityName().compareTo(zcc2.getCityName()))
					.map(zcc -> zcc.getCityName())
					.distinct()
					.forEach(city -> {
						try {
							out.write(
			/* County Name */			county
			/* County Pop */				+"\t"+ IntWithComma.format(getCountyPop(zccArray, county.toString()).sum())
			/* City Name */				+"\t"+ city
			/* City Pop */				+"\t"+ IntWithComma.format(getCityPop(zccArray, county.toString(), city.toString()).sum())
			/* No. of Zip Codes */		+"\t"+ IntWithComma.format(getCityPop(zccArray, county.toString(), city.toString()).count()) +"\n");				
						} catch (IOException e) {
							e.printStackTrace();
						}});
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
	        out.close();
	    }
			
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<zipCodeClass> zccArray = new ArrayList<zipCodeClass>();
		ArrayList<String> counties = new ArrayList<String>();

		// Initialize the Array
		initTheArray(zccArray, counties);
		
		// Get collection & sort in alphabetical order
		ArrayList<String> countyList = (ArrayList<String>) counties.stream().sorted().distinct().collect(Collectors.toList());
		
		// Write data to out file
		writeCountyData(zccArray,countyList);
	}

}
