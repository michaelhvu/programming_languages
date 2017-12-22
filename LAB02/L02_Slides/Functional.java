package L02_Slides;

import java.util.stream.IntStream;

public class Functional {
	private static boolean isPrime (int number) {
		return number >1 && IntStream.range(2,number-1).noneMatch(divisor -> number % divisor == 0);
	}

	public static void main(String[] args) {
		for (int i = 0; i<50; i++) {
			if (isPrime(i)) 
				System.out.println("The number "+i+" is prime");
		}
	}
}