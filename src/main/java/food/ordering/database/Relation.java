package food.ordering.database;

public interface Relation {
    String[] getValues();
    String getTableName();
    int getAttributesNamesId();
}
