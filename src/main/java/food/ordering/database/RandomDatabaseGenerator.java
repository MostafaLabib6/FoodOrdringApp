package food.ordering.database;

import java.util.Random;
import java.util.TreeSet;

public class RandomDatabaseGenerator {
	private Random random;
	private String[] restaurantsPaths;
	private String[] consumablesPaths;
	private String[] restaurantsNames;
	public RandomDatabaseGenerator(String[] restaurantsPaths, String[] consumablesPaths) {
		random = new Random(System.currentTimeMillis());
		this.restaurantsPaths = restaurantsPaths;
		this.consumablesPaths = consumablesPaths;
		restaurantsNames = new String[restaurantsPaths.length];
	}

	private String nextString() {
		int length = random.nextInt(20);
		char[] a = new char[length];
		for (int i = 0; i < length; ++i) {
			a[i] = (char) (random.nextInt(26) + 'a');
		}
		return String.valueOf(a);
	}
	private int nextRateValue(int count) {
		return random.nextInt(count * 4 + 1) + count;
	}
	private <T> void suffleArray(T[] a) {
		for (int i = 0; i < a.length; ++i) {
			int j = random.nextInt(a.length);
			T temp;
			temp = a[j];
			a[j] = a[i];
			a[i] = temp;
		}
	}
	public Restaurant[] getRestaurants() {
		TreeSet<String> occuerrence = new TreeSet<>();
		Restaurant[] restaurants = new Restaurant[restaurantsPaths.length];
		for (int i = 0; i < restaurants.length; ++i) {
			String restaurantName = nextString();
			
			for (; occuerrence.contains(restaurantName); restaurantName = nextString());
			occuerrence.add(restaurantName);
			
			String imagePath = restaurantsPaths[i];
			restaurantsNames[i] = restaurantName;
			int rateCount = random.nextInt(10) + 1;
			int rateValue = nextRateValue(rateCount);
			restaurants[i] = new Restaurant(restaurantName, rateCount, rateValue, imagePath);
		}
		return restaurants;
	}
	public Consumable[] getConsumables() {
		TreeSet<String> occuerrence = new TreeSet<>();
		Consumable[] consumables = new Consumable[consumablesPaths.length];
		Integer[] tmp = new Integer[consumables.length];
		String[] categories = new String[Math.max(4, consumables.length / 4)];
		for (int i = 0; i < categories.length; ++i) {
			categories[i] = nextString();
		}
		for (int i = 0; i < consumables.length; ++i) {
			tmp[i] = i;
			String consumableName = nextString();
			
			for (; occuerrence.contains(consumableName); consumableName = nextString());
			occuerrence.add(consumableName);
			
			String restaurantName = restaurantsNames[random.nextInt(restaurantsNames.length)];
			
			String category = categories[random.nextInt(categories.length)];
			
			double price = random.nextDouble();
			
			boolean isFavorite = random.nextBoolean();
			
			String imagePath = consumablesPaths[i];

			int offer = 0;

			if (random.nextBoolean()) {
				offer = random.nextInt(101);
			}

			consumables[i] = new Consumable(consumableName, restaurantName,
					category, price, isFavorite, imagePath, offer);
		}
		
		suffleArray(tmp);
		
		for (int k = 1; k < tmp.length; k += 2) {
			int i = tmp[k - 1], j = tmp[k];
			consumables[i].consumableName = consumables[j].consumableName;
			consumables[i].category = consumables[j].category;
		}
		
		return consumables;
	}
}
