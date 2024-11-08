package com.turtorials.sharedpreference_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ProductDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "Products";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    private static final String COLUMN_CATEGORY = "category";

    //constructor

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //    Đây là nơi để chúng ta viết những câu lệnh tạo bảng. Nó được gọi khi database đã được tạo
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_IMAGE_PATH + " TEXT, " +
                COLUMN_CATEGORY + " TEXT)";
        // create table productDB(id INTEGER PRIMARY KEY AUTOINCREMENT, name ....)

        db.execSQL(createTable);
    }


    //Nó được gọi khi database được nâng cấp, ví dụ như chỉnh sửa cấu trúc các bảng, thêm những thay đổi cho database,.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    Đối tượng ContentValues được sử dụng để chèn 1 hàng mới vào trong bảng cơ sở dữ liệu.
//    Mỗi đối tượng ContentValues biểu diễn một hàng và ánh xạ cột sang giá trị tương ứng.
//
//    Truy vấn trong Android được trả về như các đối tượng con trỏ (Cursor object).
//    Thay vì giải nén và trả về một bản sao của các giá trị kết quả, Cursors hoạt động như con trỏ đến một tập hợp các dữ liệu cơ bản.
//    Cursor là một cách quản lý kiểm soát vị trí của hàng trong kết quả trả về của một truy vấn cơ sở dữ liệu.
    //method to add obj Product to db
    public boolean addProduct (Product product)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // convert data from obj
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE_PATH, product.getImagePath());
        values.put(COLUMN_CATEGORY, product.getCategory());

        long result = db.insert(TABLE_PRODUCTS,null,values);
        db.close();
        return result != -1;
    }

    //method to get list product
    public List<Product> getProductsByCategory(String category)
    {
        List<Product> productList  = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query ( tên bảng, các cột cần query (null = get all), where, nhóm điều kiện của where, nhóm groupby, nhóm having, nhóm order by)
        //tham số thứ 4 (selectionArgs) trong phương thức query() yêu cầu một mảng String[]
        Cursor cursor;
        if (category.equals("All")) {
            cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
        }
        else {
            cursor = db.query(

                    TABLE_PRODUCTS,
                    null,
                    COLUMN_CATEGORY + "=?", new String[]{category},
                    null,
                    null,
                    COLUMN_NAME + " ASC");
        }
        if (cursor != null)
        {
            while(cursor.moveToNext())
            {
                int id =  cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));
                productList.add(new Product(id,name,price,imagePath,category));
            }
        }
        cursor.close();
        return productList;
    }
}
