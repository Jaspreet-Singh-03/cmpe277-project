package com.jaspreet.lab3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.jaspreet.firebaselogin.R;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_view_fragment_layout, new ListViewFragment())
                .commit();
    }
}
