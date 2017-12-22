package L02_Slides;

public class RestaurantMainF2 {

	public static void main(String[] args) {
		RestaurantClass rest = new RestaurantClass();

		rest.restaurants.forEach((name) -> System.out.println(name));
	}
}