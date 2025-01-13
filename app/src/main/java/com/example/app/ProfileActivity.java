package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileEmailTextView, profileUserIdTextView;
    private Button addProductButton, listProductsButton, logoutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        profileEmailTextView = findViewById(R.id.profileEmailTextView);
        profileUserIdTextView = findViewById(R.id.profileUserIdTextView);
        addProductButton = findViewById(R.id.addProductButton);
        listProductsButton = findViewById(R.id.listProductsButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Load user information
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            profileEmailTextView.setText(currentUser.getEmail());
            profileUserIdTextView.setText(currentUser.getUid());
        }

        // Set up button click listeners
        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProductActivity.class);
            startActivity(intent);
        });

        listProductsButton.setOnClickListener(v -> {
//            Intent intent = new Intent(ProfileActivity.this, ListProductsActivity.class);
//            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(ProfileActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close ProfileActivity to prevent back navigation
        });
    }
}
