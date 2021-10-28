package com.jaspreet.lab3;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jaspreet.firebaselogin.SignOutActivity;
import com.jaspreet.lab2.EnterDetailsActivity;
import com.jaspreet.lab2.LandingPageActivity;
import com.jaspreet.firebaselogin.R;

public class NavigationDrawerActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
   }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home : drawer.openDrawer(GravityCompat.START); break;
                case R.id.nav_home: startMapActivity(); break;
                case R.id.nav_view_orders: startLandingPageActivity(); break;
                case R.id.nav_enter_orders: startEnterDetailsActivity(); break;
                case R.id.nav_signout: startSignoutActivity(); break;
            }
           return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
           if(drawer.isDrawerOpen(GravityCompat.START)){
               drawer.closeDrawer(GravityCompat.START);
           }
           else{
            super.onBackPressed();
           }
    }

    public void startMapActivity(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }


    public void startLandingPageActivity(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
    }

    public void startEnterDetailsActivity(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        Intent intent = new Intent(this, EnterDetailsActivity.class);
        startActivity(intent);
    }

    private void startSignoutActivity() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        Intent intent = new Intent(this, SignOutActivity.class);
        startActivity(intent);
    }

    public void showToast(String text){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
    }

}