/**
 * 
 */
/**
 * @author michaelvu
 
 CSE 3302 Fall 2017
 Lab 2b
 
 This lab has one problem as described below.
 
 For this problem you must perform the following:
 
 1.    Develop only the java Functional (Lambda Expression) program solution - this MUST use lambda expressions and .stream() operations ONLY.
 2.    You are to develop and get running in Eclipse.
 a.    Code.
 i.    Main program.
 1.    You need to create the problem as a main program - the code is in the body of the program. You may create methods within this main class that does the computations called from the main method - please do not put methods in other classes.
 2.    Name this class FunctionalZipCode.java
 ii.    Data declarations
 1.    You will create a class zipCodeClass.java that contains the following elements:
 a.    int zipCode
 b.    String typeZip
 c.    String cityName
 d.    String countyName
 e.    int estPop
 iii.    Algorithm
 1.    Input.
 a.    File Name - the program will read in the file:
 L02b zip_code_database.csv
 b.    The file has 5 columns:
 a.    int zipCode
 b.    String typeZip
 c.    String cityName
 d.    String countyName
 e.    int estPop
 2.    Output.
 a.    File name. The output is written to a file:
 Problem_2b_output.txt
 b.    Output format: the file writes the following header
 
 
 
 c.    Each column is delineated by a tab character both in the header and in the subsequent data.
 d.    Each subsequent line will have the following (refer to the header when in doubt):
 a.    The county name will be listed in A-Z alphabetic order (repeated for each city in that county).
 b.    The total county population (repeated for each city in that county).
 c.    The name of each city in the county listed in A-Z alphabetic order
 d.    The total population of each city
 e.    The number of unique zip codes for each city
 e.    Output Data format
 i.    Number of cities and the number of unique zip codes are integers with no decimal places
 ii.    Population data must be integer, no decimal place and with thousands separators
 3.    Code constructs
 a.    Operations.
 a.    You are ONLY allowed to use Lambda expressions and Stream operations
 b.    You are not allowed to use Collections.sort or basic for/while loops
 b.    Data Structures. You may only use the collection ArrayList.
 c.    Exception handling - surround necessary code with try and catch blocks or throw exceptions. Your code must NOT crash when properly run but you may assume the input and output files are there and correct.
 4.    Processing notes
 a.    Notice that the data is not organized by county or city - your program must address that. You are not allowed to manipulate the input file.
 b.    For this assignment you will read and store typeZip but you will ignore it in your calculations - specifically, the population count will include all possible settings of this field for each city (settings are UNIQUE, PO BOX, STANDARD).
 c.    Duplicate city names exist across counties - specifically, more than one county may have the same city - the city of Dallas spans several counties.
 d.    Your code must not hard-code county or city names - it will need to build ArrayLists from the data file as needed.
 5.    Verification of output
 a.    How do you know what is correct? View this as a work assignment where you are given a large database and have to produce a report that can be run via your program. There is no solution to compare it with. You need to analyze the input data and determine if your output is correct.
 Submission checklist
 
 1.    All materials should be delivered in a single .zip (not .rar) file and named lastname_firstname_ID Four (4) files total as follows.
 2.    Java files - you should have 2 java files:
 a.    FunctionalZipCode.java
 b.    zipCodeClass.java
 3.    You should have 2 *.txt (files)
 a.    Problem_2b_output.txt
 b.    L02b zip_code_database.csv
 
 Uniqueness of solution. Your submittal needs to be distinct from what everyone else submits. GTAs will review each submittal - exact appearances in submittals will result in an investigation - if the investigation determines that two submittals are identical both submittals will receive a zero for the entire assignment.

 */
package L02b;
