package com.infosyialab.digifarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    Button login;
    EditText password,email;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(login.this ,MainActivity.class );
            startActivity(intent);
            finish();
        }

        login = (Button) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin();
            }
        });
    }

    private void userLogin() {

        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if(userEmail.isEmpty()){
            email.setError("Email can't be empty.");
            return;
        }

        if(userPassword.isEmpty()){
            password.setError("Password can't be empty.");
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login Please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail , userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(login.this ,MainActivity.class );
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(login.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    public void gotoReset(View view) {

        Intent i = new Intent(login.this, reset.class);
        startActivity(i);
        finish();
    }

    public void gotoRegister(View view) {

        Intent i = new Intent(login.this, register.class);
        startActivity(i);
        finish();
    }
}
