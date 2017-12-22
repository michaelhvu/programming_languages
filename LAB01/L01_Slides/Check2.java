package L01_Slides;

import java.util.function.Predicate;

public class Check2 {
	
	public static void main(String[] args) {
		Predicate<Integer> isOdd = n -> n % 2 !=0;

		for (int n=1; n<10;n++)
			System.out.println(isOdd.test(n));
	}
}