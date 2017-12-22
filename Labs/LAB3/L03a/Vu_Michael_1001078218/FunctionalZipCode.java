/*
 * Michael Vu
 * CSE 3302
 * Programming Languages
 * LAB 03a
 * Due Thursday, November 9, 2017
 */
package L03a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	
	private static String pathname = "/Users/michaelvu/eclipse-workspace/CSE 3302 Programming Languages/LAB03/L03a/";
	static int counter = 0;
	
	private static void initTheArray (ArrayList<zipCodeClass> zccArray, ArrayList<String> counties) throws FileNotFoundException,IOException {
		String [] values;
		String line = "";
		File infile = new File(pathname + "L03a zip_code_database.csv");
		BufferedReader br = new BufferedReader (new FileReader(infile));
		
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			zccArray.add(new zipCodeClass(Integer.parseInt(values[0]),values[1],values[2], values[3], Integer.parseInt(values[4])));
			counties.add(values[3]);
		}
		br.close();
	}
	
	private static IntStream getCountyPop(ArrayList<zipCodeClass> zccArray, String countyName, String cityName) {
		// method returns a stream of integer populations for the given county
		return zccArray.stream()
				.filter(zcc -> zcc.getCountyName().equals(countyName))
				.filter(zcc -> zcc.getCityName().equals(cityName))
				.mapToInt(zcc -> zcc.getEstPop());
	}

	private static IntStream getCityPop(ArrayList<zipCodeClass> zccArray, String countyName, String cityName, String zipType) {
		// method returns a stream of integer populations for the given city
		return zccArray.stream()
				.filter(zcc -> zcc.getCountyName().equals(countyName))
				.filter(zcc -> zcc.getCityName().equals(cityName))
				.filter(zcc -> zcc.getTypeZip().equals(zipType))
				.mapToInt(zcc -> zcc.getEstPop());
	}

	private static IntStream getZipStream(ArrayList<zipCodeClass> zccArray, String countyName, String cityName, String zipType) {
		// method returns a stream of zip codes for the given county, city, and zip type
		return zccArray.stream()
				.filter(zcc -> zcc.getCountyName().equals(countyName))
				.filter(zcc -> zcc.getCityName().equals(cityName))
				.filter(zcc -> zcc.getTypeZip().equals(zipType))
				.mapToInt(txc -> txc.getZipCode());
	}
	
	
	private static void writeCountyData (ArrayList<zipCodeClass> zccArray, ArrayList<String> countyList) throws IOException {
		File outfile = new File(pathname + "Problem_3a_output.txt");
		FileWriter writer = new FileWriter(outfile);
		BufferedWriter out = new BufferedWriter(writer);
		try {
		out.write("County"
				 +"\t"+"City"
				 +"\t"+"Zip Type"
				 +"\t"+"City Pop."
				 +"\t"+"First Zip"
				 +"\t"+"No. of Zip Codes"
				 +"\t"+"County Pop."+"\n");
		DecimalFormat IntWithComma = new DecimalFormat("###,###,###");
		countyList.stream()
		.forEach(county -> {
			zccArray.stream()
				.filter(zcc -> zcc.getCountyName().equals(county))
				.sorted((zcc1, zcc2) -> zcc1.getCityName().compareTo(zcc2.getCityName()))
				.map(zcc -> zcc.getCityName())
				.distinct()
				.forEach(city -> {
					ArrayList<String> zipTypes = new ArrayList<String>();
					zipTypes = (ArrayList<String>) zccArray.stream()
							.filter(zcc -> zcc.getCountyName().equals(county.toString()))
							.filter(zcc -> zcc.getCityName().equals(city))
							.map(zipCodeClass::getTypeZip)
							.distinct()
							.sorted()
							.collect(Collectors.toList());
					counter = 0;
							zipTypes.stream()
							.forEach(zipType -> {
								long NumZipTypes = zccArray.stream().filter(zcc -> zcc.getCountyName().equals(county.toString())).filter(zcc -> zcc.getCityName().equals(city)).map(zipCodeClass::getTypeZip).count();
								int min = getZipStream(zccArray,county.toString(), city.toString(), zipType.toString()).reduce(Integer.MAX_VALUE, (x,y) -> x<y? x:y);
								counter++;
								try {
									if(counter == 1) {
										out.write(
												/* HEADER */
												/* County Name */			county
												/* City Name */				+"\t"+ city
												/* Zip Type */				+"\t"+ zipType
												/* City Pop */				+"\t"+ IntWithComma.format(getCityPop(zccArray, county.toString(), city.toString(), zipType.toString()).sum())
												/* First Zip */				+"\t"+ min
												/* No. of Zip Codes */		+"\t"+ IntWithComma.format(getZipStream(zccArray, county.toString(), city.toString(), zipType.toString()).count())
												/* County Pop. */			+"\t"+ IntWithComma.format(getCountyPop(zccArray, county.toString(), city.toString()).sum())+"\n");
									}
									if (NumZipTypes > 1 && counter >= 2) {
										out.write(
												/* County Name */			"\t"
												/* City Name */				+"\t"
												/* Zip Type */				+ zipType
												/* City Pop */				+"\t"+ IntWithComma.format(getCityPop(zccArray, county.toString(), city.toString(), zipType.toString()).sum())
												/* First Zip */				+"\t"+ min
												/* No. of Zip Codes */		+"\t"+ IntWithComma.format(getZipStream(zccArray, county.toString(), city.toString(), zipType.toString()).count())
												/* County Pop. */			+"\t"+"\n");
									}
								} catch (IOException e) {
									e.printStackTrace();
								}});
						});
				});
			}catch (IOException e) {
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
