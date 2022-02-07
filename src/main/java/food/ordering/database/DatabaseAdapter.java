package food.ordering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodordringapp.R;


public class DatabaseAdapter extends SQLiteOpenHelper implements Adapter2 {
    private final Context context;

    public DatabaseAdapter(Context context) {
        super(context, context.getResources().getString(R.string.database_name), null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(context.getResources().getString(R.string.restaurants_table_creation_statement));
        db.execSQL(context.getResources().getString(R.string.consumables_table_creation_statement));
        db.execSQL(context.getResources().getString(R.string.cart_table_creation_statement));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(context.getResources().getString(R.string.cart_table_drop_statement));
        db.execSQL(context.getResources().getString(R.string.consumables_table_drop_statement));
        db.execSQL(context.getResources().getString(R.string.restaurants_table_drop_statement));
        onCreate(db);
    }

    public boolean isDatabaseIntegrityOk() {
        SQLiteDatabase db = getReadableDatabase();
        boolean ok = db.isDatabaseIntegrityOk();
        db.close();
        return ok;
    }

    public boolean isEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM RESTAURANTS;", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0;
    }

    @Override
    public Restaurant[] getRestaurants() {
        SQLiteDatabase db = getReadableDatabase();
        String[] attributesNames =
                context.getResources().getStringArray(R.array.restaurant_attributes);
        Cursor cursor = db.query(Restaurant.TABLE_NAME, attributesNames,
                null, null, null, null,
                attributesNames[1] + " / " + attributesNames[2] + " DESC");
        if (cursor == null) { return null; }
        cursor.moveToFirst();
        Restaurant[] restaurants = new Restaurant[cursor.getCount()];
        if (cursor.getCount() > 0) {
            int i = 0;
            do {
                restaurants[i++] = new Restaurant(cursor.getString(0),
                        cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurants;
    }

    @Override
    public Consumable[] getConsumables(String restaurantName, String category, boolean isOffer) {
        SQLiteDatabase db = getReadableDatabase();
        String[] attributesNames =
                context.getResources().getStringArray(R.array.consumables_attributes);
        String selection = null;
        String[] selectionArgs = null;
        if (restaurantName != null) {
            selection = attributesNames[1] + " = ?";
            selectionArgs = new String[]{ restaurantName };
        }
        if (category != null) {
            if (selectionArgs == null) {
                selection = attributesNames[2] + " = ?";
                selectionArgs = new String[]{ category };
            } else {
                selection = selection.concat(" AND " + attributesNames[2] + " = ?");
                selectionArgs = new String[]{ selectionArgs[0], category };
            }
        }
        if (isOffer) {
            if (selectionArgs == null) {
                selection = attributesNames[6] + " = ?";
                selectionArgs = new String[]{ category };
            } else {
                selection = selection.concat(" AND " + attributesNames[6] + " > ?");
                String[] nStrings = new String[selectionArgs.length + 1];
                for (int i = 0; i < selectionArgs.length; ++i) {
                    nStrings[i] = selectionArgs[i];
                }
                nStrings[selectionArgs.length] = "0";
                selectionArgs = nStrings;
            }
        }
        Cursor cursor = db.query(Consumable.TABLE_NAME, attributesNames,
                selection, selectionArgs, null, null, null);
        if (cursor == null) { return null; }
        cursor.moveToFirst();
        Consumable[] consumables = new Consumable[cursor.getCount()];
        if (cursor.getCount() > 0) {
            int i = 0;
            do {
                consumables[i++] = new Consumable(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getString(4).equals("1") ? true : false,
                        cursor.getString(5), cursor.getInt(6));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return consumables;
    }

    @Override
    public Purchase[] getCart() {
        SQLiteDatabase db = getReadableDatabase();
        String[] attributesNames =
                context.getResources().getStringArray(R.array.cart_attributes);
        Cursor cursor = db.query(Purchase.TABLE_NAME, attributesNames,
                null, null, null, null, null);
        if (cursor == null) { return null; }
        cursor.moveToFirst();
        Purchase[] purchases = new Purchase[cursor.getCount()];
        if (cursor.getCount() > 0) {
            int i = 0;
            do {
                purchases[i++] = new Purchase(
                        cursor.getString(0), cursor.getString(1),
                        cursor.getDouble(2), cursor.getInt(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return purchases;
    }

    @Override
    public Consumable[] getFavorites() {
        /*
        Handle it differently
         */
        SQLiteDatabase db = getReadableDatabase();
        String[] attributesNames =
                context.getResources().getStringArray(R.array.consumables_attributes);
        Cursor cursor = db.query(Consumable.TABLE_NAME, attributesNames,
                attributesNames[4] + " = ?", new String[]{ "1" },
                null, null, null);
        if (cursor == null) { return null; }
        cursor.moveToFirst();
        Consumable[] consumables = new Consumable[cursor.getCount()];
        if (cursor.getCount() > 0) {
            int i = 0;
            do {
                consumables[i++] = new Consumable(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getString(4).equals("1") ? true : false,
                        cursor.getString(5), cursor.getInt(6));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return consumables;
    }

    @Override
    public boolean purchaseCart() {
        SQLiteDatabase db = getWritableDatabase();
        int numberOfEntries = db.delete(Purchase.TABLE_NAME, null, null);
        db.close();
        return numberOfEntries > 0;
    }

    @Override
    public boolean setFavorite(String consumableName, String restaurantName,
                               boolean addedToFavorite) {
        SQLiteDatabase db = getWritableDatabase();
        String[] attributesNames =
                context.getResources().getStringArray(R.array.consumables_attributes);
        ContentValues contentValues = new ContentValues();
        contentValues.put(attributesNames[4], addedToFavorite ? "1" : "0");
        String whereClause = attributesNames[0] + " = ? AND " + attributesNames[1] + " = ?";
        String[] whereArgs = new String[]{ consumableName, restaurantName };
        int altered = db.update(Consumable.TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
        return altered > 0;
    }

    @Override
    public void addToCart(Purchase purchase) {
        insert(purchase);
    }

    @Override
    public String[] getCategories(String restaurant) {
        String[] attributesNames =
                context.getResources().getStringArray(R.array.consumables_attributes);
        String whereClause = null;
        String[] whereArgs = null;
        if (restaurant != null) {
            whereClause = attributesNames[1] + " = ?";
            whereArgs = new String[] { restaurant };
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, Consumable.TABLE_NAME,
                new String[]{ attributesNames[2] }, whereClause, whereArgs,
                null, null, null, null);
        if (cursor == null) { return null; }
        cursor.moveToFirst();
        String[] categories = new String[cursor.getCount()];
        if (cursor.getCount() > 0) {
            int i = 0;
            do {
                categories[i++] = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public void insert(Relation relation) {
        ContentValues contentValues = new ContentValues();
        String[] attributesNames
                = context.getResources().getStringArray(relation.getAttributesNamesId());
        String[] values = relation.getValues();
        assert(values.length == attributesNames.length);
        for (int i = 0; i < values.length; ++i) {
            contentValues.put(attributesNames[i], values[i]);
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(relation.getTableName(), null, contentValues);
        db.close();
    }
    public void deleteTitle(String name,String name2)
    {SQLiteDatabase db = getWritableDatabase();
         db.delete("CONSUMABLES", "CONS_NAME" + "=" + "?" +"and "+"REST_NAME" + "=" + "?", new String[]{name,name2}) ;
    }

    public void updateRate(String restaurantName){
    SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RATE_COUNT" ,4);
        String whereClause = "REST_NAME like ?" ;
        String[] whereArgs = new String[]{  restaurantName };
         db.update("RESTAURANTS", contentValues, whereClause, whereArgs);
        db.close();
    }
}
