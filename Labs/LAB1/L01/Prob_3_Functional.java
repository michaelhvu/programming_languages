import java.util.Scanner;
import java.util.function.BinaryOperator;

public class Prob_3_Functional {
		
	BinaryOperator<Integer> gcd = (x,y) -> y == 0 ? x : this.gcd.apply(y, x % y);

public static void main(String args[]){  
		//a.Implement a function that computes the Greatest Common Denominator (GCD) for the given two integer inputs.
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the first number: ");
		int n1 = scan.nextInt(); // Scans the next token of the input as an int.
		System.out.println("Enter the second number: ");
		int n2 = scan.nextInt(); // Scans the next token of the input as an int.  
		System.out.println(new Prob_3_Functional().gcd.apply(n1,n2));  
		scan.close();
		
		//b.
		System.out.println();
		for (int i=0; i<10;i++)
			for (int j=0; j<10;j++)
				System.out.println(new Prob_3_Functional().gcd.apply(i,j));  
	}
}