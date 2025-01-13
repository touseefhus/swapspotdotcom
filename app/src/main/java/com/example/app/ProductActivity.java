package com.example.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText productName, productCategory, productDescription;
    private ImageView productImage;
    private Uri imageUri;
    private DatabaseReference databaseReference;

    private String exchangeName = "";
    private String exchangeDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productCategory = findViewById(R.id.productCategory);
        productImage = findViewById(R.id.productImageView);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        Button submitButton = findViewById(R.id.submitProductButton);
        Button exchangeButton = findViewById(R.id.openReplaceDetailsButton);

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        // Image picker action
        selectImageButton.setOnClickListener(v -> openImagePicker());

        // Submit button action
        submitButton.setOnClickListener(v -> validateAndSave());

        // Exchange button action
        exchangeButton.setOnClickListener(v -> showExchangeDialog());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                productImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void validateAndSave() {
        String name = productName.getText().toString().trim();
        String category = productCategory.getText().toString().trim();
        String description = productDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            productName.setError("Product name is required");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            productDescription.setError("Product description is required");
            return;
        }

        if (TextUtils.isEmpty(category)) {
            productCategory.setError("Product category is required");
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Product image is required", Toast.LENGTH_SHORT).show();
            return;
        }

        saveProductToDatabase(name, category, description, exchangeName, exchangeDescription);
    }

    private void saveProductToDatabase(String name, String category, String description, String exchangeName, String exchangeDescription) {
        String productId = databaseReference.push().getKey();
        String userId = "currentUserId"; // Replace with actual user ID retrieval logic.

        if (productId != null) {
            Map<String, Object> product = new HashMap<>();
            product.put("name", name);
            product.put("category", category);
            product.put("description", description);
            product.put("imageUri", imageUri.toString());
            product.put("userId", userId);

            if (!TextUtils.isEmpty(exchangeName) && !TextUtils.isEmpty(exchangeDescription)) {
                Map<String, String> exchange = new HashMap<>();
                exchange.put("name", exchangeName);
//                exchange.put("description", exchangeDescription);
                product.put("exchange", exchange);
            }

            databaseReference.child(productId).setValue(product)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Product saved successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error saving product: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void showExchangeDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_exchange_product, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        EditText exchangeProductName = dialogView.findViewById(R.id.exchangeProductName);
        EditText exchangeProductDescription = dialogView.findViewById(R.id.exchangeProductDescription);
        Button dialogSubmitButton = dialogView.findViewById(R.id.dialogSubmitButton);

        dialogSubmitButton.setOnClickListener(v -> {
            exchangeName = exchangeProductName.getText().toString().trim();
            exchangeDescription = exchangeProductDescription.getText().toString().trim();

            if (TextUtils.isEmpty(exchangeName)) {
                exchangeProductName.setError("Exchange product name is required");
                return;
            }

            if (TextUtils.isEmpty(exchangeDescription)) {
                exchangeProductDescription.setError("Exchange product description is required");
                return;
            }

            Toast.makeText(this, "Exchange details added!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
