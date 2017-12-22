import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Prob_2_Functional {
	
	UnaryOperator<Integer> fibo = i -> i == 0 ? 0 : i == 1 ? 1 : this.fibo.apply( i - 1) + this.fibo.apply(i - 2);
	
	public static void main(String args[]){  
		//a.	Implement a function that computes the Fibonacci sequence for the given Integer input.
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int n = scan.nextInt(); // Scans the next token of the input as a long integer.
		System.out.println(new Prob_2_Functional().fibo.apply(n));  
		scan.close();
		
		//b.	Print out the first 16 Fibonacci numbers (0 to 15 inclusive). Use a for loop in the main method to do this.
		System.out.println();
		for(int i=0;i<=15;i++){
			System.out.println(new Prob_2_Functional().fibo.apply(i));
		}
	}
}
