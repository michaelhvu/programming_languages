package L03a_Solution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import L02b_Solution.zipCodeClass;

public class FunctionalZipCode {
	
	private static String [] values;
	private static String pathname="//DISKSTATION/Family_Folder/John/UTA/2017/Fall/CSE 3302/Labs/Lab 3/L03a/";
	
	private static void initZipArray (ArrayList<zipCodeClass> zipArray) throws FileNotFoundException,IOException {
		File infile1 = new File(pathname+"Assignment/L03a zip_code_database.csv");
		String line = "";
		BufferedReader br = new BufferedReader (new FileReader(infile1));
		
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			zipArray.add(new zipCodeClass(Integer.parseInt(values[0]),values[1],values[2],values[3],Integer.parseInt(values[4])));
		}
		br.close();
	}

	private static Stream<zipCodeClass> getStream (ArrayList<zipCodeClass> zipArray,String countyName, String cityName) {
		return zipArray.stream().filter(zip -> zip.getCountyName().equals(countyName)).filter(zip -> zip.getCityName().equals(cityName));
	}
	
	private static void writeZipData (ArrayList<zipCodeClass> zipArray) throws IOException {
		DecimalFormat IntWithComma=new DecimalFormat("###,###,###");
		File outfile = new File(pathname+"Solution/Problem_3a_output.txt");
				FileWriter writer = new FileWriter(outfile);
				writer.write("County\tCity\tZip Type\tCity Pop\tFirst Zip\tNo. Zips\tCounty Pop\n");
				zipArray.stream()
				.map(cnty -> cnty.getCountyName()).distinct().sorted()

				.forEach (cnty -> {
								ArrayList<String> cityNames = new ArrayList<String>();
								cityNames = (ArrayList<String>) zipArray.stream().filter(citcou -> citcou.getCountyName().equals(cnty.toString())).map(zipCodeClass::getCityName).distinct().sorted().collect(Collectors.toList());
								cityNames.stream()
								.forEach(city -> {
									int poboxPop = getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("PO BOX")).mapToInt(zip -> zip.getEstPop()).reduce(0, (x,y) -> x+y);
									int poboxPopcnt = (int) getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("PO BOX")).mapToInt(zip -> zip.getEstPop()).count();
									int standardPop = getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("STANDARD")).mapToInt(zip -> zip.getEstPop()).reduce(0, (x,y) -> x+y);
									int standardPopcnt = (int) getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("STANDARD")).mapToInt(zip -> zip.getEstPop()).count();
									int uniquePop = getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("UNIQUE")).mapToInt(zip -> zip.getEstPop()).reduce(0, (x,y) -> x+y);
									int uniquePopcnt = (int) getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("UNIQUE")).mapToInt(zip -> zip.getEstPop()).count();
									String poboxZips = getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("PO BOX")).map(zip -> String.valueOf(zip.getZipCode())).sorted().limit(1).collect(Collectors.joining(","));
									String standardZips = getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("STANDARD")).map(zip -> String.valueOf(zip.getZipCode())).sorted().limit(1).collect(Collectors.joining(","));
									String uniqueZips = getStream(zipArray,cnty.toString(),city.toString()).filter(zip -> zip.getTypeZip().equals("UNIQUE")).map(zip -> String.valueOf(zip.getZipCode())).sorted().limit(1).collect(Collectors.joining(","));
									int cityPop = getStream(zipArray,cnty.toString(),city.toString()).mapToInt(c -> c.getEstPop()).reduce(0, (x,y) -> x+y);

									try {
										if (poboxPopcnt>0) {
											writer.write(cnty.toString()+"\t"+city.toString()+"\tPO BOX\t"+IntWithComma.format(poboxPop)+"\t"+poboxZips+"\t"+poboxPopcnt+"\t"+IntWithComma.format(cityPop)+"\n");
											if (standardPopcnt>0)
												writer.write("\t\tSTANDARD\t"+IntWithComma.format(standardPop)+"\t"+standardZips+"\t"+standardPopcnt+"\n");
											if (uniquePopcnt>0)
												writer.write("\t\tUNIQUE\t"+IntWithComma.format(uniquePop)+"\t"+uniqueZips+"\t"+uniquePopcnt+"\n");
											}
										else
											if (standardPopcnt>0) {
												writer.write(cnty.toString()+"\t"+city.toString()+"\tSTANDARD\t"+IntWithComma.format(standardPop)+"\t"+standardZips+"\t"+standardPopcnt+"\t"+IntWithComma.format(cityPop)+"\n");
												if (uniquePopcnt>0)
													writer.write("\t\tUNIQUE\t"+IntWithComma.format(uniquePop)+"\t"+uniqueZips+"\t"+uniquePopcnt+"\n");													
											}
											else
												if (uniquePopcnt>0)
													writer.write(cnty.toString()+"\t"+city.toString()+"\tUNIQUE\t"+IntWithComma.format(uniquePop)+"\t"+uniqueZips+"\t"+uniquePopcnt+"\t"+IntWithComma.format(cityPop)+"\n");
										} catch (IOException e) {
											e.printStackTrace(); }
										});
							});
				writer.close();
	}

	public static void main(String[] args) throws FileNotFoundException,IOException {
		ArrayList<zipCodeClass> zipArray = new ArrayList<zipCodeClass>();
		initZipArray(zipArray);
		writeZipData(zipArray);
	}
}
