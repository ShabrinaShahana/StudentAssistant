package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout welcomeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeLayout = findViewById(R.id.welcomeLayout);

        findViewById(R.id.button1).setOnClickListener(v -> openFragment(new MyTasksFragment()));
        findViewById(R.id.button2).setOnClickListener(v -> openFragment(new PersonalFinanceAssistantFragment()));
        findViewById(R.id.button3).setOnClickListener(v -> openFragment(new AiNoteTakerFragment()));
    }

    private void openFragment(Fragment fragment) {
        welcomeLayout.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            findViewById(R.id.welcomeLayout).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
