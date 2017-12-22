package L02_Slides;

public class RestaurantMainI1 {

	public static void main(String[] args) {
		RestaurantClass rest = new RestaurantClass();

		for (int i=0; i < rest.restaurants.size(); i++ )
			System.out.println(rest.restaurants.get(i));
	}
}