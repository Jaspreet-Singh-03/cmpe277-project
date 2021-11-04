package com.jaspreet.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jaspreet.lab2.ElementModel;
import com.jaspreet.lab2.FoodType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignOutActivity extends AppCompatActivity {
    private TextView heading;
    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signout_layout);
        String message = "Current User : " + FirebaseAuth.getInstance().getCurrentUser().getEmail();
        heading = findViewById(R.id.mainTextView);
        heading.setText(message);
        signOutBtn = findViewById(R.id.google_signout_button);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        finishAffinity();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}