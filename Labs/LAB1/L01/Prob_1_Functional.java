import java.util.*;
import java.util.function.UnaryOperator;

public class Prob_1_Functional {
	
    UnaryOperator<Long> fact = i -> i == 0 ? 1 : i * this.fact.apply( i - 1);

    public static void main(String[] args) {
    		//a.	Implement a function that computes the factorial for the given Integer input.
    		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number: ");
		long n = scan.nextLong(); // Scans the next token of the input as a long integer.
        System.out.println(new Prob_1_Functional().fact.apply(n));  
		scan.close();
		
		//b.	Print out the first 16 factorial numbers (0 to 15 inclusive). Use a for loop in the main method to do this.
		System.out.println();
		for(long i=0;i<=15;i++){
			System.out.println(new Prob_1_Functional().fact.apply(i));
		}
    }
}
