package L01_Slides;

public class isPrimeImperative {

	private static boolean isPrime(int number) {
		boolean hasDivisors=false;
		for (int i=2; i<number && !hasDivisors; i++)
			hasDivisors=(number %i == 0);
		return !hasDivisors && number>1;
	}
	
	public static void main(String[] args) {
		for (int i=1; i<=100; i++)
			if (isPrime(i))
				System.out.println("The number "+i+" is a prime number");
	}

}
