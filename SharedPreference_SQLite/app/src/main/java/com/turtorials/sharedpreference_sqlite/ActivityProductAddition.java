package com.turtorials.sharedpreference_sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityProductAddition extends AppCompatActivity {

    private EditText etProductName, etProductPrice;
    private Spinner spProductImage, spProductCategory;
    private Button btnAddProduct, btnResetProduct;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_addition);

        // Initialize UI elements
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        spProductImage = findViewById(R.id.spProductImage);
        spProductCategory = findViewById(R.id.spProductCategory);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnResetProduct = findViewById(R.id.btnResetProduct);

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Set up Reset button functionality
        btnResetProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();
            }
        });

        // Set up Add Product button functionality
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProductDetails();
            }
        });
    }

    // Method to reset all inputs
    private void resetInputs() {
        etProductName.setText("");
        etProductPrice.setText("");
        spProductImage.setSelection(0);
        spProductCategory.setSelection(0);
        Toast.makeText(this, "Đã reset thông tin sản phẩm", Toast.LENGTH_SHORT).show();
    }

    // Method to save product details in SQLite
    private void saveProductDetails() {
        String productName = etProductName.getText().toString().trim();
        String productPriceText = etProductPrice.getText().toString().trim();
        String productImage = spProductImage.getSelectedItem().toString();
        String productCategory = spProductCategory.getSelectedItem().toString();

        if (productName.isEmpty() || productPriceText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        double productPrice;
        try {
            productPrice = Double.parseDouble(productPriceText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Map the image name to the drawable resource ID path
        String imagePath = "@drawable/" + productImage;

        // Create a Product object
        Product product = new Product(productName, productPrice, imagePath, productCategory);

        // Save product in database
        boolean success = databaseHelper.addProduct(product);
        if (success) {
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            resetInputs();
        } else {
            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
