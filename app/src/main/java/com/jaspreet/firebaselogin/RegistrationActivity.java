package com.jaspreet.firebaselogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity : ";
    private FirebaseAuth mAuth;
    private Button registerBtn;
    private Button resetBtn;
    private EditText email;
    private EditText password1;
    private EditText password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.register_button);
        resetBtn = findViewById(R.id.reset_register_button);
        email = findViewById(R.id.register_user_email);
        password1 = findViewById(R.id.register_user_password1);
        password2 = findViewById(R.id.register_user_password2);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                registerUser();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                resetText();
            }
        });

    }

    public void registerUser(){
        String email = this.email.getText().toString();
        String password1 = this.password1.getText().toString();
        String password2 = this.password2.getText().toString();

        if(email.isEmpty() || password1.isEmpty() || password2.isEmpty()){
            makeToast("Please fill up all the required fields");
            return;
        }

        if(!password1.equals(password2)){
            makeToast("Password's doesn't match");
            return;
        }
        String password = password1;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            makeToast("Registartion Successful");
                            resetText();
                            moveToLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            makeToast("Registration failed.");
                        }
                    }
                });
    }

    public void resetText(){
        this.email.setText("");
        this.password1.setText("");
        this.password2.setText("");
        this.email.setHint("Enter your email");
        this.password1.setHint("Enter your password");
        this.password2.setHint("Confirm your password");
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void moveToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }

    public void makeToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
