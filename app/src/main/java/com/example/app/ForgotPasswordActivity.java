package com.example.app;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize views
        emailEditText = findViewById(R.id.forgotPasswordEmail);
        submitButton = findViewById(R.id.submitForgotPasswordButton);

        // Set click listener for the submit button
        submitButton.setOnClickListener(view -> sendResetLink());
    }

    private void sendResetLink() {
        // Get email input
        String email = emailEditText.getText().toString().trim();

        // Validate email
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return;
        }

        // Simulate sending a reset link
        Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_LONG).show();

        // Clear the input field
        emailEditText.setText("");
    }
}
