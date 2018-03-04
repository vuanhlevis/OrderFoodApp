package com.example.vuanhlevis.orderfoods.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.vuanhlevis.orderfoods.models.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuanhlevis on 04/03/2018.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "OrderFoodDB";
    private static final int DB_VER = 1;
    private static final String TAG = "Database";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCart() {
        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductID", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";

        builder.setTables(sqlTable);
        Cursor cursor = builder.query(database, sqlSelect,
                null, null, null, null, null);

        final List<Order> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("ProductID")) ;

                result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductID")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format(
                "INSERT INTO OrderDetail(ProductID,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(), order.getProductName(), order.getQuantity(),
                order.getPrice(), order.getDiscount());

        database.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        database.execSQL(query);
    }
}
