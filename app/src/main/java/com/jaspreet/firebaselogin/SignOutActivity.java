package com.jaspreet.firebaselogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignOutActivity extends AppCompatActivity {
    private TextView heading;
    private Button signOutBtn;
    private RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signout_layout);
        heading = findViewById(R.id.mainTextView);
        signOutBtn = findViewById(R.id.google_signout_button);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        service = RetrofitClient.getInstance().create(RetrofitService.class);

        getCall();

    }

    private void getCall() {
        Call<List<RetroModel>> data = service.getAll();
        data.enqueue(new Callback<List<RetroModel>>() {
            @Override
            public void onResponse(Call<List<RetroModel>> call, Response<List<RetroModel>> response) {
                showData(response.body());
            }

            @Override
            public void onFailure(Call<List<RetroModel>> call, Throwable t) {
                Toast.makeText(SignOutActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showData(List<RetroModel> body) {
        if(body!=null && body.size()>0) {
            heading.setText(body.toString());
            Toast.makeText(this, body.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void signOut(){
       // FirebaseAuth.getInstance().signOut();
       // finish();
        RetroModel entity = new RetroModel();
        entity.setName("Ravi");
        service.add(entity).enqueue(new Callback<RetroModel>() {
            @Override
            public void onResponse(Call<RetroModel> call, Response<RetroModel> response) {
                heading.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<RetroModel> call, Throwable t) {
                t.toString();
            }
        });
    }
}