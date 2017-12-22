package L01_Slides;

public class Check3 {

	interface square {
		int method(int n);
	}
	
	public static void main(String[] args) {
		square squareFunction = (int n) ->  n*n;
		System.out.println(squareFunction.method(4));
	}
}
