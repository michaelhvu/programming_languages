/*
 * Michael Vu
 * CSE 3302
 * Programming Languages
 * LAB 03b
 * Due Monday, November 27, 2017
 */

package lab03b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class RealEstateMainFunctional {
	
	private static String pathname = "/Users/michaelvu/eclipse-workspace/CSE 3302 Programming Languages/LAB03/lab03b/";

	private static IntStream getRating(Integer zipCode, ArrayList<ZipRateTable> zrtArray) {
		return zrtArray.stream()
				.filter(zrt -> zrt.getZipCode() == zipCode)
				.mapToInt(zrt -> zrt.getRating())
				.distinct();
	}
	
	private static void initTheArray(ArrayList<ZipRateTable> zrtArray, ArrayList<RealEstateClass> recArray, ArrayList<Integer> zipCodes) throws FileNotFoundException,IOException {
		String [] values;
		String line = "";
		
		File infile0 = new File(pathname + "ZipRateTable.csv");
		BufferedReader br0 = new BufferedReader (new FileReader(infile0));
		while ((line = br0.readLine()) != null) {
			values = line.split(",");
			zrtArray.add(new ZipRateTable(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
		}
		br0.close();
		
		File infile1 = new File(pathname + "House Data.csv");
		BufferedReader br1 = new BufferedReader (new FileReader(infile1));
		while ((line = br1.readLine()) != null) {
			values = line.split(",");
			int rating = getRating(Integer.parseInt(values[3]), zrtArray).sum();;
			recArray.add(new RealEstateClass(values[0], values[1], values[2], Integer.parseInt(values[3]), Integer.parseInt(values[4]), 
					Double.parseDouble(values[5]), Double.parseDouble(values[6]), values[7], Integer.parseInt(values[8]), Integer.parseInt(values[9]), 
					Integer.parseInt(values[10]), Integer.parseInt(values[11]), Integer.parseInt(values[12]), Integer.parseInt(values[13]), rating));
			zipCodes.add(Integer.parseInt(values[3]));
		}
		br1.close();
	}
	
	private static IntStream getZipStream(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToInt(rec -> rec.getZip());
	}
	
	private static IntStream getPrice(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToInt(rec -> rec.getPrice());
	}
	
	private static IntStream getSqft(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToInt(rec -> rec.getSquare_Feet());
	}
	
	private static DoubleStream getBeds(ArrayList<RealEstateClass> recArray, Integer zipCode) {
	return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToDouble(rec -> rec.getBeds());
	}
	
	private static DoubleStream getBaths(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToDouble(rec -> rec.getBaths());
	}
	
	private static DoubleStream getDOM(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToDouble(rec -> rec.getDays_On_Market());
	}
	
	private static DoubleStream getYrBlt(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToDouble(rec -> rec.getYear_Built());
	}
	private static DoubleStream getHOA(ArrayList<RealEstateClass> recArray, Integer zipCode) {
		return recArray.stream()
				.filter(rec -> rec.getZip() == zipCode)
				.mapToDouble(rec -> rec.getHOA_Per_Month());
	}
	
	private static void writeZipData(ArrayList<RealEstateClass> recArray, ArrayList<Integer> zipList) throws IOException {
		DecimalFormat IntWithComma = new DecimalFormat("###,###,###");
		DecimalFormat OnePlace = new DecimalFormat("###.0");
		DecimalFormat Year = new DecimalFormat("####");
		DecimalFormat Money = new DecimalFormat("###,###,###.00");
		DecimalFormat TwoPlaces = new DecimalFormat("###,###,###.##");
		
		File outfile1 = new File(pathname+"HouseOrderByRating.txt");
				FileWriter writer1 = new FileWriter(outfile1);
				writer1.write("Type\t" + "Address\t" + "City\t" + "Zip\t" + "Price\t" + "Beds\t" + "Baths\t" + "Location\t" + "Sqft\t" + 
						      "Lot size\t" + "YrBlt\t" + "DOM\t" + "$/SqFt\t" + "HOA/mth\t" + "Rank Grp\t" + "Percnt SqFt" + "\n");
				ArrayList<RealEstateClass> RankOne = new ArrayList<RealEstateClass>();
				RankOne = (ArrayList<RealEstateClass>) recArray.stream()
						.filter(rec -> rec.getRating() > 6)
						.filter(rec -> rec.getProperty_Type().equals("Single Family Residential"))
						.filter(rec -> rec.getPrice() < 200000)
						.filter(rec -> rec.getDollar_Per_Sq_Ft() < 110)
						.filter(rec -> rec.getSquare_Feet() >= 1750)
						.filter(rec -> rec.getSquare_Feet() <= 2500)
						.filter(rec -> rec.getYear_Built() >= 2007)
						.filter(rec -> rec.getHOA_Per_Month() <= 25)
						.collect(Collectors.toList());

				RankOne.stream()
				.sorted(Comparator.comparing(RealEstateClass::getPrice))
				.sorted(Comparator.comparing(RealEstateClass::getDollar_Per_Sq_Ft))
				.forEach(rec -> {
					int totalPrice = getPrice(recArray, rec.getZip()).sum();
					int totalSqft = getSqft(recArray, rec.getZip()).sum();
					double avgDolPerSqFt = (double) totalPrice/totalSqft;
					double percSqFt = (double) 100 * rec.getDollar_Per_Sq_Ft()/avgDolPerSqFt;
					try {
					writer1.write(
						/* Type         */           rec.getProperty_Type()
						/* Address      */ + "\t"  + rec.getAddress()
						/* City         */ + "\t"  + rec.getCity()
						/* Zip          */ + "\t"  + rec.getZip()
						/* Price        */ + "\t$" + IntWithComma.format(rec.getPrice())
						/* Beds         */ + "\t"  + OnePlace.format(rec.getBeds())
						/* Baths        */ + "\t"  + OnePlace.format(rec.getBaths())
						/* Location     */ + "\t"  + rec.getLocation()
						/* Sqft         */ + "\t"  + IntWithComma.format(rec.getSquare_Feet())
						/* Lot size     */ + "\t"  + IntWithComma.format(rec.getLot_Size())
						/* YrBlt        */ + "\t"  + Year.format(rec.getYear_Built())
						/* DOM          */ + "\t"  + rec.getDays_On_Market()
						/* $/SqFt       */ + "\t$" + IntWithComma.format(rec.getDollar_Per_Sq_Ft())
						/* HOA/mth      */ + "\t$" + IntWithComma.format(rec.getHOA_Per_Month())
						/* Rank Group   */ + "\t"  + "1"
						/* Percent SqFt */ + "\t"  + OnePlace.format(percSqFt) + "\n");
					}catch (IOException e) {
						e.printStackTrace();
					}});
				
				ArrayList<RealEstateClass> RankTwo = new ArrayList<RealEstateClass>();
				RankTwo = (ArrayList<RealEstateClass>) recArray.stream()
						.filter(rec -> rec.getRating() > 6)
						.filter(rec -> rec.getProperty_Type().equals("Single Family Residential"))
						.filter(rec -> rec.getPrice() < 200000)
						.filter(rec -> rec.getDollar_Per_Sq_Ft() < 110)
						.filter(rec -> rec.getSquare_Feet() >= 1750)
						.filter(rec -> rec.getSquare_Feet() <= 2500)
						.filter(rec -> rec.getYear_Built() >= 2007)
						.filter(rec -> rec.getHOA_Per_Month() > 25)
						.filter(rec -> rec.getHOA_Per_Month() <= 30)
						.collect(Collectors.toList());
				
				RankTwo.stream()
				.sorted(Comparator.comparing(RealEstateClass::getPrice))
				.sorted(Comparator.comparing(RealEstateClass::getDollar_Per_Sq_Ft))
				.forEach(rec -> {
					int totalPrice = getPrice(recArray, rec.getZip()).sum();
					int totalSqft = getSqft(recArray, rec.getZip()).sum();
					double avgDolPerSqFt = (double) totalPrice/totalSqft;
					double percSqFt = (double) 100 * rec.getDollar_Per_Sq_Ft()/avgDolPerSqFt;
					try {
					writer1.write(
						/* Type         */           rec.getProperty_Type()
						/* Address      */ + "\t"  + rec.getAddress()
						/* City         */ + "\t"  + rec.getCity()
						/* Zip          */ + "\t"  + rec.getZip()
						/* Price        */ + "\t$" + IntWithComma.format(rec.getPrice())
						/* Beds         */ + "\t"  + OnePlace.format(rec.getBeds())
						/* Baths        */ + "\t"  + OnePlace.format(rec.getBaths())
						/* Location     */ + "\t"  + rec.getLocation()
						/* Sqft         */ + "\t"  + IntWithComma.format(rec.getSquare_Feet())
						/* Lot size     */ + "\t"  + IntWithComma.format(rec.getLot_Size())
						/* YrBlt        */ + "\t"  + Year.format(rec.getYear_Built())
						/* DOM          */ + "\t"  + rec.getDays_On_Market()
						/* $/SqFt       */ + "\t$" + IntWithComma.format(rec.getDollar_Per_Sq_Ft())
						/* HOA/mth      */ + "\t$" + IntWithComma.format(rec.getHOA_Per_Month())
						/* Rank Group   */ + "\t"  + "2"
						/* Percent SqFt */ + "\t"  + OnePlace.format(percSqFt) + "\n");
					}catch (IOException e) {
						e.printStackTrace();
					}});
				
				ArrayList<RealEstateClass> RankThree = new ArrayList<RealEstateClass>();
				RankThree = (ArrayList<RealEstateClass>) recArray.stream()
						.filter(rec -> rec.getRating() > 6)
						.filter(rec -> rec.getProperty_Type().equals("Single Family Residential"))
						.filter(rec -> rec.getPrice() < 200000)
						.filter(rec -> rec.getDollar_Per_Sq_Ft() < 110)
						.filter(rec -> rec.getSquare_Feet() >= 1750)
						.filter(rec -> rec.getSquare_Feet() <= 2500)
						.filter(rec -> rec.getYear_Built() >= 2000)
						.filter(rec -> rec.getYear_Built() < 2007)
						.filter(rec -> rec.getHOA_Per_Month() <= 25)
						.collect(Collectors.toList());
				
				RankThree.stream()
				.sorted(Comparator.comparing(RealEstateClass::getPrice))
				.sorted(Comparator.comparing(RealEstateClass::getDollar_Per_Sq_Ft))
				.forEach(rec -> {
					int totalPrice = getPrice(recArray, rec.getZip()).sum();
					int totalSqft = getSqft(recArray, rec.getZip()).sum();
					double avgDolPerSqFt = (double) totalPrice/totalSqft;
					double percSqFt = (double) 100 * rec.getDollar_Per_Sq_Ft()/avgDolPerSqFt;
					try {
					writer1.write(
						/* Type         */           rec.getProperty_Type()
						/* Address      */ + "\t"  + rec.getAddress()
						/* City         */ + "\t"  + rec.getCity()
						/* Zip          */ + "\t"  + rec.getZip()
						/* Price        */ + "\t$" + IntWithComma.format(rec.getPrice())
						/* Beds         */ + "\t"  + OnePlace.format(rec.getBeds())
						/* Baths        */ + "\t"  + OnePlace.format(rec.getBaths())
						/* Location     */ + "\t"  + rec.getLocation()
						/* Sqft         */ + "\t"  + IntWithComma.format(rec.getSquare_Feet())
						/* Lot size     */ + "\t"  + IntWithComma.format(rec.getLot_Size())
						/* YrBlt        */ + "\t"  + Year.format(rec.getYear_Built())
						/* DOM          */ + "\t"  + rec.getDays_On_Market()
						/* $/SqFt       */ + "\t$" + IntWithComma.format(rec.getDollar_Per_Sq_Ft())
						/* HOA/mth      */ + "\t$" + IntWithComma.format(rec.getHOA_Per_Month())
						/* Rank Group   */ + "\t"  + "3"
						/* Percent SqFt */ + "\t"  + OnePlace.format(percSqFt) + "\n");
					}catch (IOException e) {
						e.printStackTrace();
					}});
				
				ArrayList<RealEstateClass> RankFour = new ArrayList<RealEstateClass>();
				RankFour = (ArrayList<RealEstateClass>) recArray.stream()
						.filter(rec -> rec.getRating() > 6)
						.filter(rec -> rec.getProperty_Type().equals("Single Family Residential"))
						.filter(rec -> rec.getPrice() < 200000)
						.filter(rec -> rec.getDollar_Per_Sq_Ft() < 110)
						.filter(rec -> rec.getSquare_Feet() >= 1750)
						.filter(rec -> rec.getSquare_Feet() <= 2500)
						.filter(rec -> rec.getYear_Built() >= 2000)
						.filter(rec -> rec.getYear_Built() < 2007)
						.filter(rec -> rec.getHOA_Per_Month() > 25)
						.filter(rec -> rec.getHOA_Per_Month() <= 30)
						.collect(Collectors.toList());
				
				RankFour.stream()
				.sorted(Comparator.comparing(RealEstateClass::getPrice))
				.sorted(Comparator.comparing(RealEstateClass::getDollar_Per_Sq_Ft))
				.forEach(rec -> {
					int totalPrice = getPrice(recArray, rec.getZip()).sum();
					int totalSqft = getSqft(recArray, rec.getZip()).sum();
					double avgDolPerSqFt = (double) totalPrice/totalSqft;
					double percSqFt = (double) 100 * rec.getDollar_Per_Sq_Ft()/avgDolPerSqFt;
					try {
					writer1.write(
						/* Type         */           rec.getProperty_Type()
						/* Address      */ + "\t"  + rec.getAddress()
						/* City         */ + "\t"  + rec.getCity()
						/* Zip          */ + "\t"  + rec.getZip()
						/* Price        */ + "\t$" + IntWithComma.format(rec.getPrice())
						/* Beds         */ + "\t"  + OnePlace.format(rec.getBeds())
						/* Baths        */ + "\t"  + OnePlace.format(rec.getBaths())
						/* Location     */ + "\t"  + rec.getLocation()
						/* Sqft         */ + "\t"  + IntWithComma.format(rec.getSquare_Feet())
						/* Lot size     */ + "\t"  + IntWithComma.format(rec.getLot_Size())
						/* YrBlt        */ + "\t"  + Year.format(rec.getYear_Built())
						/* DOM          */ + "\t"  + rec.getDays_On_Market()
						/* $/SqFt       */ + "\t$" + IntWithComma.format(rec.getDollar_Per_Sq_Ft())
						/* HOA/mth      */ + "\t$" + IntWithComma.format(rec.getHOA_Per_Month())
						/* Rank Group   */ + "\t"  + "4"
						/* Percent SqFt */ + "\t"  + OnePlace.format(percSqFt) + "\n");
					}catch (IOException e) {
						e.printStackTrace();
					}});
				writer1.close();
				
		File outfile2 = new File(pathname+"HouseAveragesByZip.txt");
				FileWriter writer2 = new FileWriter(outfile2);
				writer2.write("Zip Code\t" + "No. Homes\t" + "Ave Price\t" + "Ave Sqft\t" + "Ave Beds\t" + "Ave Baths\t" + 
						      "Ave $/SqFt\t" + "Ave DOM\t" + "Ave YrBlt\t" + "Ave HOA" + "\n");
				zipList.stream()
				.forEach(zip -> {
					int numHomes = (int) getZipStream(recArray, zip).count();
					int totalPrice = getPrice(recArray, zip).sum();
					int totalSqft = getSqft(recArray, zip).sum();
					double aveBeds = getBeds(recArray, zip).sum() / (double) numHomes;
					double aveBaths = getBaths(recArray, zip).sum() / (double) numHomes;
					double aveDOM = getDOM(recArray, zip).sum() / (double) numHomes;
					double aveYrBlt = getYrBlt(recArray, zip).sum() / (double) numHomes;
					double totalHOA = getHOA(recArray, zip).sum();
					try {
						writer2.write(
						/*  Zip Code   */           zip
						/*  No. Homes  */ + "\t"  + IntWithComma.format(numHomes)
						/*  Ave Price  */ + "\t$" + IntWithComma.format(totalPrice/numHomes)
						/*  Ave Sqft   */ + "\t"  + IntWithComma.format(totalSqft/numHomes)
						/*  Ave Beds   */ + "\t"  + OnePlace.format(aveBeds)
						/*	Ave Baths  */ + "\t"  + OnePlace.format(aveBaths)
						/*	Ave $/Sqft */ + "\t$" + TwoPlaces.format((double)totalPrice/(double)totalSqft)
						/*	Ave DOM    */ + "\t"  + OnePlace.format(aveDOM)
						/*	Ave YrBlt  */ + "\t"  + Year.format(aveYrBlt)
						/*	Ave HOA    */ + "\t$" + Money.format((double)totalHOA/(double)numHomes) +"\n");
					} catch (IOException e) {
						e.printStackTrace();
					}});
				writer2.close();			
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<RealEstateClass> recArray = new ArrayList<RealEstateClass>();
		ArrayList<ZipRateTable> zrtArray = new ArrayList<ZipRateTable>();
		ArrayList<Integer> zipCodes = new ArrayList<Integer>();

		// Initialize the Array
		initTheArray(zrtArray, recArray, zipCodes);
		
		// Get collection & sort in alphabetical order
		ArrayList<Integer> zipList = (ArrayList<Integer>) zipCodes.stream().sorted().distinct().collect(Collectors.toList());
	
		// Write data to out file
		writeZipData(recArray, zipList);
	}
}
