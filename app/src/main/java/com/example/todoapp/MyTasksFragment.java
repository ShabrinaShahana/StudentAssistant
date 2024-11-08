package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyTasksFragment extends Fragment {
    private static final String TAG = "MainActivity"; // Keep the same tag for debugging
    TaskPagerAdapter fragmentAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        // Find views using the same variable names
        viewPager = rootView.findViewById(R.id.viewPager);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        FloatingActionButton fab = rootView.findViewById(R.id.fab);

        // Edge-to-edge setup
        ViewCompat.setOnApplyWindowInsetsListener(rootView.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragmentAdapter = new TaskPagerAdapter(requireActivity());
        viewPager.setAdapter(fragmentAdapter);

        // Attach TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Todo");
            } else if (position == 1) {
                tab.setText("Completed");
            }
        }).attach();

        // FloatingActionButton to add new tasks
        fab.setOnClickListener(view -> AddNewTask.newInstance().show(getParentFragmentManager(), AddNewTask.TAG));

        return rootView;
    }

    public void updateView() {
        Log.i(TAG, "updateView");
        TodoFragment todoFragment = (TodoFragment) getParentFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());

        if (todoFragment != null) {
            todoFragment.updateView();
        }
    }
}
