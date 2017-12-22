package L02_Slides;

public class RestaurantMainF3 {

	public static void main(String[] args) {
		RestaurantClass rest = new RestaurantClass();

		rest.restaurants.forEach(System.out::println);
	}
}