package food.ordering.database;


import com.example.foodordringapp.R;

public class Purchase implements Relation {
    public static String TABLE_NAME = "CART";
    public String consumableName;
    public String restaurantName;
    public double unitPrice;
    public int quantity;
    public Purchase(String consumableName, String restaurantName,
                    double unitPrice, int quantity) {
        this.consumableName = consumableName; this.restaurantName = restaurantName;
        this.unitPrice = unitPrice; this.quantity = quantity;
    }

    @Override
    public String[] getValues() {
        return new String[]{
                consumableName, restaurantName,
                String.valueOf(unitPrice),
                String.valueOf(quantity)
        };
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public int getAttributesNamesId() {
        return R.array.cart_attributes;
    }
}
