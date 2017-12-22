package L01_Slides;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Check1 {
	
	public interface TestChar {
		boolean isCharY(char x);
	}
	
	public static void main(String[] args) {
		TestChar charfield = (char x) -> x=='y';
		Predicate<Integer> isOdd = n -> n % 2 !=0;
		UnaryOperator<Integer> square = i -> i*i;
		BinaryOperator<Integer> mathExpr = (a, b) -> a * a + b * b;
		BinaryOperator<Integer> sum = (x, y) -> x + y;
		Supplier<Integer> fortyTwo = () -> 42;
		Supplier<Double> pi = () -> 3.14;
		Consumer<String> printString = (s) -> System.out.println(s);
		
		for (int i=0;i<10;i++)
			System.out.println(isOdd.test(i)+" "+square.apply(i)+" "+mathExpr.apply(i,2)+" "+sum.apply(i,2)+" "+fortyTwo.get()+" "+pi.get());
		System.out.println(charfield.isCharY('n')+" "+charfield.isCharY('y')+" ");
		printString.accept("Finished!");
	}
}