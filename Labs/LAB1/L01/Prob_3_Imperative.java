import java.util.Scanner;

public class Prob_3_Imperative {
	
	public static int gcd(int x, int y) {
	    if (y == 0)
	        return x;
	    else
	        return gcd(y, x % y);
	}
	
	public static void main(String args[]){  
		//a.Implement a function that computes the Greatest Common Denominator (GCD) for the given two integer inputs.
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the first number: ");
		int n1 = scan.nextInt(); // Scans the next token of the input as an int.
		System.out.println("Enter the second number: ");
		int n2 = scan.nextInt(); // Scans the next token of the input as an int.
		int gcd1 = gcd(n1, n2);   
		System.out.println(gcd1);  
		scan.close();
		
		//b.
		System.out.println();
		for (int i=0; i<10;i++)
			for (int j=0; j<10;j++)
				System.out.println(gcd(i,j));
	}
}
