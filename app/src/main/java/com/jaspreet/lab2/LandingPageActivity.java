package com.jaspreet.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.jaspreet.firebaselogin.R;

public class LandingPageActivity extends AppCompatActivity {

    private static final String TAG = "LandingPageActivity";
    private static final int SUCCESS_REQUEST_CODE = 999;
    Button btn_start_get_info, btn_view_details;
    Toolbar main_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page_layout);
        Log.d(TAG, "onCreate: Main Activity Created");

        btn_start_get_info = findViewById(R.id.btn_start_get_info);
        btn_view_details = findViewById(R.id.btn_view_details);
        main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Food Share");
        ab.setIcon(R.drawable.app_logo);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Fragment viewDetails = new ViewDetailsFragment();

        btn_start_get_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEnterDetailsActivity();
            }
        });

        btn_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, viewDetails)
                        .commit();
            }
        });

        DatabaseHelper.getInstance().getUserData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void startEnterDetailsActivity(){
        Intent intent = new Intent(this, EnterDetailsActivity.class);
        Toast.makeText(this,"Enter Food Order Details", Toast.LENGTH_SHORT).show();
        startActivityForResult(intent, SUCCESS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent returnIntent) {
        super.onActivityResult(requestCode,resultCode,returnIntent);
        if(resultCode == RESULT_OK ){
            if(requestCode == SUCCESS_REQUEST_CODE && returnIntent!=null ){
                String count = returnIntent.getStringExtra("count");
                String message = "Number of Records Added : " + count;
                Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "Food Sharing App v1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}