import java.util.Scanner;

public class Prob_2_Imperative {

	static long fibonacci(int n)  {
	    if(n == 0)
	        return 0;
	    else if(n == 1)
	      return 1;
	   else
	      return fibonacci(n - 1) + fibonacci(n - 2);
	}
	
	public static void main(String args[]){  
		//a.	Implement a function that computes the Fibonacci sequence for the given Integer input.
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int n = scan.nextInt(); // Scans the next token of the input as an int.
		long fibo = fibonacci(n);   
		System.out.println(fibo);  
		scan.close();
		
		//b.	Print out the first 16 Fibonacci numbers (0 to 15 inclusive). Use a for loop in the main method to do this.
		System.out.println();
		for(int i=0;i<=15;i++){
			fibo = fibonacci(i);
			System.out.println(fibo);
		}
	}
}
