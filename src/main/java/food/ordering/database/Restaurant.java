package food.ordering.database;


import com.example.foodordringapp.R;

public class Restaurant implements Relation {
    public static String TABLE_NAME = "RESTAURANTS";
    public String restaurantName;
    public int rateCount;
    public int rateValue;
    public String imagePath;
    public Restaurant(String restaurantName, int rateCount, int rateValue, String imagePath) {
        this.restaurantName = restaurantName; this.rateCount = rateCount;
        this.rateValue = rateValue; this.imagePath = imagePath;
    }

    @Override
    public String[] getValues() {
        return new String[]{restaurantName, String.valueOf(rateCount), String.valueOf(rateValue), imagePath };
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getAttributesNamesId() {
        return R.array.restaurant_attributes;
    }
}
