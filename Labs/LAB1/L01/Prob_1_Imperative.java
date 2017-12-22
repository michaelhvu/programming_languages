import java.util.Scanner; 

public class Prob_1_Imperative {
	static long factorial(int n){    
		if (n == 0)    
			return 1;    
		else    
			return(n * factorial(n-1));    
	}    
	public static void main(String args[]){  
		//a.	Implement a function that computes the factorial for the given Integer input.
		long fact = 1;  
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int n = scan.nextInt(); // Scans the next token of the input as an int.
		fact = factorial(n);   
		System.out.println(fact);  
		scan.close();
		
		//b.	Print out the first 16 factorial numbers (0 to 15 inclusive). Use a for loop in the main method to do this.
		System.out.println();
		int i;
		fact = 1;
		for(i=0;i<=15;i++){
			fact = factorial(i);
			System.out.println(fact);
		}
	}
} 	
