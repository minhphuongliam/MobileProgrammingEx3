package com.turtorials.sharedpreference_sqlite;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityUserCreation extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String USER_NAME_KEY = "userName";

    private TextView tvUserName;
    private EditText inputInfo;
    private Button btnCreateUser, btnViewList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        // Initialize views
        tvUserName = findViewById(R.id.tvUserName);
        inputInfo = findViewById(R.id.inputInfo);
        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnViewList = findViewById(R.id.btnViewList);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load user name from SharedPreferences, or set default as "User" if not found
        String userName = sharedPreferences.getString(USER_NAME_KEY, "User");
        tvUserName.setText(userName);

        // Set up Create User button
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserName = inputInfo.getText().toString().trim();

                if (!newUserName.isEmpty()) {
                    // Save the new user name to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_NAME_KEY, newUserName);
                    editor.apply();

                    // Update TextView to show the new user name
                    tvUserName.setText(newUserName);
                }
            }
        });

        // Set up View List button
        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ActivityProductList
                Intent intent = new Intent(ActivityUserCreation.this, ActivityProductList.class);
                startActivity(intent);
            }
        });
    }
}
