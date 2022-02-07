package food.ordering.database;

public interface Adapter2 {
    Restaurant[] getRestaurants();
    Consumable[] getConsumables(String restaurantName, String category, boolean isOffer);
    Purchase[] getCart();
    Consumable[] getFavorites();
    boolean purchaseCart();
    boolean setFavorite(String consumableName, String restaurantName, boolean addedToFavorite);
    void addToCart(Purchase purchase);
    String[] getCategories(String restaurant);
}
