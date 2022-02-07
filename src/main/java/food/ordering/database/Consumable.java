package food.ordering.database;


import com.example.foodordringapp.R;

public class Consumable implements Relation {
    public static String TABLE_NAME = "CONSUMABLES";
    public String consumableName;
    public String restaurantName;
    public String category;
    public double price;
    public boolean favorite;
    public String imagePath;
    public int offer;
    public Consumable(String consumableName, String restaurantName, String category,
                      double price, boolean favorite, String imagePath, int offer) {
        this.consumableName = consumableName;
        this.restaurantName = restaurantName;
        this.category = category;
        this.price = price;
        this.favorite = favorite;
        this.imagePath = imagePath;
        this.offer = offer;
    }

    @Override
    public String[] getValues() {
        return new String[]{
                consumableName, restaurantName, category,
                String.valueOf(price), favorite ? "1" : "0", imagePath,
                String.valueOf(offer)
        };
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getAttributesNamesId() {
        return R.array.consumables_attributes;
    }
}
