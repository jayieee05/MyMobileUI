package com.example.mymobileui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is logged in
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String phone = prefs.getString("phone", "");
        String password = prefs.getString("password", "");

        if (phone.isEmpty() || password.isEmpty()) {
            // Not logged in → redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return; // stop loading MainActivity
        }

        // Load MainActivity UI
        setContentView(R.layout.activity_main);

        // Profile Image
        ImageView profileImage = findViewById(R.id.profile_image);

        profileImage.setOnClickListener(v -> {
            showLogoutMenu(v);
        });
    }

    private void showLogoutMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenu().add("Logout");

        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("Logout")) {
                // Clear login info
                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                // Go back to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        popup.show();
    }
}
